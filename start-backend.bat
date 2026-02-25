@echo off
title AI Log Analyzer - Full Backend Starter
echo ==============================================
echo starting AI Log Analyzer Backend System...
echo ==============================================

:: Phase 1: Infrastructure
echo [1/7] starting Docker Infrastructure...
docker-compose up -d
echo Waiting for Kafka and Databases to be ready (20s)...
timeout /t 20 /nobreak

:: Phase 2: Common Library
echo [2/7] Installing Common Library...
cd common-library
call mvn clean install -DskipTests
cd ..

:: Phase 3: Core Services
echo [3/7] starting API Gateway (8080)...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10 /nobreak

echo [4/7] starting Ingestion Service (8089)...
start "Ingestion Service" cmd /k "cd log-ingestion-service && mvn spring-boot:run"
timeout /t 5 /nobreak

echo [5/7] starting Parsing Service (8082)...
start "Parsing Service" cmd /k "cd log-parsing-service && mvn spring-boot:run"
timeout /t 5 /nobreak

:: Phase 4: Logic Services
echo [6/7] starting AI Analysis Service (8084)...
start "AI Analysis" cmd /k "cd ai-analysis-service && mvn spring-boot:run"

echo [7/7] starting Alert Service (8085)...
start "Alert Service" cmd /k "cd alert-service && mvn spring-boot:run"

echo ==============================================
echo All services are starting in separate windows!
echo ==============================================
pause