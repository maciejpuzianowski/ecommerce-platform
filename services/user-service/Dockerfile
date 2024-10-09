# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar to the container
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8086

# Run the jar file
ENTRYPOINT ["java", "-jar", "user-service.jar"]