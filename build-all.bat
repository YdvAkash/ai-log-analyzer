@echo off
echo Building all microservices...
call mvn clean install -DskipTests
echo.
echo Build complete!
pause
