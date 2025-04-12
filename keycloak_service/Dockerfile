FROM python:3.9-slim

WORKDIR /app

# Install curl for the wait-for-it script
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY app.py requirements.txt ./

RUN pip install --no-cache-dir -r requirements.txt

# Wait for Keycloak to be ready
COPY wait-for-it.sh /
RUN chmod +x /wait-for-it.sh

# Run with gunicorn for production
CMD ["/wait-for-it.sh", "keycloak:8080", "--", "gunicorn", "--bind", "0.0.0.0:5001", "app:app"]