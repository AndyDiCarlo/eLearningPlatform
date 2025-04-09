# E-Learning Platform
Course project implementing an e-learning system with microservices
#
### To build the code as a docker image, open a command-line window to the *elearningplatform* project directory and execute the following command:
```
$ mvn clean package dockerfile:build -DskipTests
```

### Use docker-compose to start the actual image.  To start the docker image, run the following command: 
```
$ docker-compose -f docker/docker-compose.yml up
```

### To use realms effectively, users must be populated into the realm. This is done at localhost:5000 using the created api or by using the Keycloak UI.
#
### Links to Repos
Main Repo: https://github.com/AndyDiCarlo/eLearningPlatform

Configuration Repo: https://github.com/Nicholas-Bava/elearning_config

#
Project by Andy DiCarlo and Nick Bava

Baylor MS Computer Science, CSI 5347
