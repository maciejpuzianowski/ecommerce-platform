# About
Simple ecommerce-platform where I wanted to show my skills connected to microservice architecture development.
# Tech stack
- Java 17
- SpringBoot Cloud
- Maven
- Docker
# Project
Project consists of 5 microservices that are connected between each other to handle different steps of online shopping.
Moreover, config server is used to keep all the configs in one place and distribute it to all microservices. Eureka server is used as discovery service to get rid of raw URIs in each microservice. Finally, api gateway is used to route load-balanced requests to specific microservice.
# Deployment
The entire project is already containerized, and you can try to run it yourself using start.sh script that I provided. (Docker, Maven and Java 17 is needed)