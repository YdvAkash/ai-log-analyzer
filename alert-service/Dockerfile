# Multi-stage build for Alert Service
# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Copy entire project from root context
COPY . /build
WORKDIR /build

# Build alert-service module and its dependencies
RUN mvn clean package -DskipTests -pl alert-service -am

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /build/alert-service/target/alert-service-1.0.0.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.JarLauncher || exit 1

# Set spring profiles
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
