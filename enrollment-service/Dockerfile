# Stage 1: Build and Unpack the Jar
FROM openjdk:17-slim as build

# Add Maintainer Info
LABEL maintainer="Andy DiCarlo & Nick Bava"

# The application's jar file
ARG JAR_FILE

# Copy the application's jar to the container
COPY ${JAR_FILE} app.jar

# Unpackage the jar file into a dependency directory
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

# Stage 2: Create the Runtime Image
FROM openjdk:17-slim

# Add a volume for temporary files
VOLUME /tmp

# Build argument for the dependency folder path
ARG DEPENDENCY=/target/dependency

# Copy the unpackaged contents from the build stage into the /app directory
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app


# Execute the application by specifying the main class
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.elearning.enrollmentservice.EnrollmentServiceApplication"]