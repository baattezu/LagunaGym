@echo off

REM Запуск docker-compose
echo Starting docker-compose...
docker-compose up -d || (echo Failed to start docker-compose && exit /b 1)

echo Services started successfully.
