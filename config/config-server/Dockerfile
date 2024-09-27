# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar to the container
COPY target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar
COPY ./configs /configs

# Expose the port your Spring Boot app runs on
EXPOSE 8888

# Run the jar file
ENTRYPOINT ["java", "-jar", "config-server.jar"]