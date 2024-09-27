#!/bin/bash

# Function to build each Maven project
build_maven_servers() {
  services=("config/config-server" "discovery/eureka-server")

  for service in "${services[@]}"; do
    echo "Building $service..."
    # Change into the service directory
    cd "$service" || { echo "Failed to enter $service directory"; exit 1; }

    # Run Maven clean and install
    mvn clean install -DskipTests || { echo "Maven build failed for $service"; exit 1; }

    # Change back to the root directory
    cd ..
    cd ..
  done
}

# Function to build each Maven project
build_maven_services() {
  services=("common-models" "inventory-service" "product-service" "order-service" "payment-service")

  for service in "${services[@]}"; do
    echo "Building $service..."
    # Change into the service directory
    cd "services/$service" || { echo "Failed to enter $service directory"; exit 1; }

    # Run Maven clean and install
    mvn clean install -DskipTests || { echo "Maven build failed for $service"; exit 1; }

    # Change back to the root directory
    cd ..
    cd ..
  done
}

# Function to run Docker Compose
run_docker_compose() {
  echo "Starting services with Docker Compose..."
  cd docker || { echo "Failed to enter docker directory"; exit 1; }
  docker-compose up --build -d || { echo "Failed to start services with Docker Compose"; exit 1; }
}

# Main script execution
echo "Starting build and deployment process..."

# Step 1: Build the Maven projects
build_maven_servers
build_maven_services

# Step 2: Run Docker Compose
run_docker_compose

echo "All services are up and running!"