# Stage 1: Build the application
FROM ubuntu:latest AS build

# Update package manager and install necessary tools
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    maven \
    && apt-get clean

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project (skipping tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Create a smaller runtime image
FROM ubuntu:latest

# Install only JDK for runtime
RUN apt-get update && apt-get install -y \
    openjdk-21-jre \
    && apt-get clean

# Set working directory
WORKDIR /app

# Expose the application port
EXPOSE 8099

# Copy the WAR file from the build stage
COPY --from=build /app/target/api-product-0.0.1-SNAPSHOT.war app.war

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.war"]