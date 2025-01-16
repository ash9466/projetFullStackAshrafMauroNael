# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-21 as build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml ./
COPY src ./src

# Build the application and skip the tests
RUN mvn clean install -DskipTests

# Stage 2: Create the final image with OpenJDK
FROM openjdk:21-jdk

# Set the working directory in the final image
WORKDIR /app

# Expose the port the application will run on
EXPOSE 8080

# Copy the built jar file from the Maven stage
COPY --from=build /app/target/api-1.0.0-SNAPSHOT.jar /app/app.jar

# Set environment variables for the application
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV FRONT_END_DOMAIN=${FRONT_END_DOMAIN}
ENV JWT_ACCESS_SECRET=${JWT_ACCESS_SECRET}
ENV JWT_REFRESH_SECRET=${JWT_ACCESS_SECRET}
ENV ACCESS_TOKEN_EXPIRATION=${ACCESS_TOKEN_EXPIRATION}
ENV REFRESH_TOKEN_EXPIRATION=${REFRESH_TOKEN_EXPIRATION}

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
