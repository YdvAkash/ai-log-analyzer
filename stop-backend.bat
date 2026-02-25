@echo off
title AI Log Analyzer - Full Backend Stopper
echo ==============================================
echo Stopping AI Log Analyzer System...
echo ==============================================

:: Phase 1: Stop Java Microservices
echo [1/2] Terminating all Spring Boot/Java processes...
:: Yeh command saare running Java processes ko force-kill karegi
taskkill /F /IM java.exe /T >nul 2>&1
if %errorLevel% == 0 (
    echo Successfully stopped all Java microservices.
) else (
    echo No running Java processes found or already stopped.
)

:: Phase 2: Down Docker Infrastructure
echo [2/2] Taking down Docker containers and networks...
docker-compose down
if %errorLevel% == 0 (
    echo Docker infrastructure is now DOWN.
) else (
    echo [ERROR] Failed to stop Docker. Is Docker Desktop running?
)

echo ==============================================
echo Cleanup Complete! All ports are now free.
echo ==============================================
pause