@echo off
echo Starting Infrastructure Services...
docker-compose up -d
echo.
echo Waiting for services to start (60 seconds)...
timeout /t 60 /nobreak
echo.
echo Infrastructure services started!
pause
