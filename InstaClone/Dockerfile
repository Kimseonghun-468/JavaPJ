# Stage 1: Build the application using Gradle
FROM gradle:7.5.1-jdk17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src ./src

# Build the application
RUN ./gradlew build -x test

# Stage 2: Create the final image with OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application with the Docker profile
ENTRYPOINT ["java", "-jar", "app.jar"]