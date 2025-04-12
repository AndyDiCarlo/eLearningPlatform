from flask import Flask, request, jsonify
import requests
import os

app = Flask(__name__)

# Configuration from environment variables
keycloak_base_url = os.environ.get("KEYCLOAK_URL", "http://localhost:8080")
admin_username = os.environ.get("KEYCLOAK_ADMIN", "admin")
admin_password = os.environ.get("KEYCLOAK_ADMIN_PASSWORD", "admin")
target_realm = os.environ.get("TARGET_REALM", "elearn-realm")
admin_realm = "master"
client_id_for_admin = "admin-cli"

role_mapping = {
    "ADMIN": "elearning-admin",
    "USER": "elearning-user"
}

# Token caching
admin_token = None
token_expiry = 0


# Get admin token with caching
def get_admin_token():
    global admin_token, token_expiry
    import time

    current_time = time.time()
    if admin_token and token_expiry > current_time:
        return admin_token

    token_url = f"{keycloak_base_url}/realms/{admin_realm}/protocol/openid-connect/token"
    data = {
        "grant_type": "password",
        "client_id": client_id_for_admin,
        "username": admin_username,
        "password": admin_password,
    }
    response = requests.post(token_url, data=data)
    if response.status_code != 200:
        raise Exception("Failed to get admin token")

    response_data = response.json()
    admin_token = response_data.get("access_token")
    # Set expiry 10 seconds before actual expiry
    token_expiry = current_time + response_data.get("expires_in", 60) - 10

    return admin_token


# Check if user exists
def user_exists(username):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/users?username={username}"
    headers = {"Authorization": f"Bearer {token}"}
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        raise Exception("User lookup failed")
    users_found = response.json()
    return len(users_found) > 0


# Get user ID by username
def get_user_id(username):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/users?username={username}"
    headers = {"Authorization": f"Bearer {token}"}
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        raise Exception("User lookup failed")
    users_found = response.json()
    if not users_found:
        raise Exception("No user found with username: " + username)
    return users_found[0]["id"]


# Get realm role representation
def get_realm_role_representation(role_name):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/roles/{role_name}"
    headers = {"Authorization": f"Bearer {token}"}
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        raise Exception("Role lookup failed")
    return response.json()


# Check if user has role
def user_has_role(user_id, role_name):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/users/{user_id}/role-mappings/realm"
    headers = {"Authorization": f"Bearer {token}"}
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        raise Exception("Role lookup failed")
    assigned_roles = response.json()
    return any(role["name"] == role_name for role in assigned_roles)


# Create user
def create_user(username, email, first_name, last_name, password):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/users"
    headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}
    payload = {
        "username": username,
        "enabled": True,
        "email": email,
        "emailVerified": True,
        "firstName": first_name,
        "lastName": last_name,
        "credentials": [
            {
                "type": "password",
                "value": password,
                "temporary": False
            }
        ]
    }
    response = requests.post(url, json=payload, headers=headers)
    if response.status_code not in [201, 204]:
        raise Exception("User creation failed")

    # Get the user ID
    return get_user_id(username)


# Assign role to user
def assign_realm_role_to_user(user_id, role_representation):
    token = get_admin_token()
    url = f"{keycloak_base_url}/admin/realms/{target_realm}/users/{user_id}/role-mappings/realm"
    headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}
    payload = [role_representation]
    response = requests.post(url, json=payload, headers=headers)
    if response.status_code not in [200, 204]:
        raise Exception("Role assignment failed")


# Request password reset
def request_password_reset(username):
    token = get_admin_token()
    try:
        user_id = get_user_id(username)

        url = f"{keycloak_base_url}/admin/realms/{target_realm}/users/{user_id}"
        headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}

        # Get current user representation
        response = requests.get(url, headers=headers)
        if response.status_code != 200:
            raise Exception("Failed to get user details")

        user_rep = response.json()

        # Add required action for reset password
        if "requiredActions" not in user_rep:
            user_rep["requiredActions"] = []

        if "UPDATE_PASSWORD" not in user_rep["requiredActions"]:
            user_rep["requiredActions"].append("UPDATE_PASSWORD")

        # Update user
        response = requests.put(url, json=user_rep, headers=headers)
        if response.status_code not in [200, 204]:
            raise Exception("Failed to set password reset action")

        # Send email verification
        email_url = f"{keycloak_base_url}/admin/realms/{target_realm}/users/{user_id}/execute-actions-email"
        actions = ["UPDATE_PASSWORD"]
        response = requests.put(email_url, json=actions, headers=headers)
        if response.status_code not in [200, 204]:
            raise Exception("Failed to send password reset email")

        return True
    except Exception as e:
        print(f"Error requesting password reset: {str(e)}")
        return False


# API Endpoints
@app.route('/api/users', methods=['POST'])
def register_user():
    data = request.json

    if not all(k in data for k in ["username", "email", "firstName", "lastName", "password", "role"]):
        return jsonify({"error": "Missing required fields"}), 400

    try:
        if user_exists(data["username"]):
            return jsonify({"message": "User already exists", "status": "existing"}), 200

        user_id = create_user(
            data["username"],
            data["email"],
            data["firstName"],
            data["lastName"],
            data["password"]
        )

        realm_role_name = role_mapping.get(data["role"])
        if not realm_role_name:
            return jsonify({"error": f"No mapping for role: {data['role']}"}), 400

        role_representation = get_realm_role_representation(realm_role_name)
        assign_realm_role_to_user(user_id, role_representation)

        return jsonify({
            "message": "User created successfully",
            "userId": user_id,
            "status": "created"
        }), 201

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/api/users/reset-password', methods=['POST'])
def reset_password():
    data = request.json

    if "username" not in data:
        return jsonify({"error": "Username is required"}), 400

    try:
        if not user_exists(data["username"]):
            return jsonify({"error": "User does not exist"}), 404

        success = request_password_reset(data["username"])

        if success:
            return jsonify({"message": "Password reset email sent"}), 200
        else:
            return jsonify({"error": "Failed to send password reset email"}), 500

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int(os.environ.get('PORT', 5001)))
