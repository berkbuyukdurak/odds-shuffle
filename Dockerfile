# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/odds-shuffle-0.0.1-SNAPSHOT.jar /app/odds-shuffle.jar

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/odds-shuffle.jar"]