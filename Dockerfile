# Use a Gradle image with a compatible version for Spring Boot 3.3.0
FROM gradle:8.1-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle project files
COPY . .

# Build the project
RUN gradle build --no-daemon

# Use a minimal Java runtime image for the final stage
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/build/libs/*.jar /app/

# Expose the application port (adjust according to your application's port)
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "meetingplanner-0.0.1-SNAPSHOT.jar"]
