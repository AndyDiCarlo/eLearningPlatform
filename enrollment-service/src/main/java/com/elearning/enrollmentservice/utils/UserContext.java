package com.elearning.enrollmentservice.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String USER_SERVICE_ID = "tmx-user-service-id";

    private String correlationId= new String();
    private String authToken= new String();
    private String userId = new String();
    private String userServiceId = new String();

    public String getCorrelationId() { return correlationId;}
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public String getUserServiceId() {
		return userId;
	}
	public void setUserServiceId(String userId) {
		this.userId = userId;
	}



}
