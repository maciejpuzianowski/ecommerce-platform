# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar to the container
COPY target/inventory-service-0.0.1-SNAPSHOT.jar /app/inventory-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]