
FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

RUN mvn clean package

# Base image with JRE
FROM openjdk:11-jre-slim AS runtime

# Copy the artifact from the build image to the runtime image
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar /app/test-0.0.1-SNAPSHOT.jar

# Expose the port used by the application
EXPOSE 8080

# Run the application when the container starts
CMD ["java", "-jar", "/app/test-0.0.1-SNAPSHOT.jar"]