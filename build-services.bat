@echo off

:: Определяем массив директорий микросервисов
set services=api-gateway auth-service config-service eureka membership-service notification-service user-service

:: Сборка каждого микросервиса
for %%s in (%services%) do (
  echo Building %%s...
  cd %%s
  if errorlevel 1 (
    echo Failed to access %%s directory
    exit /b 1
  )
  call mvnw.cmd spring-boot:build-image -DskipTests
  if errorlevel 1 (
    echo Build failed for %%s
    exit /b 1
  )
  cd ..
)

echo All services built successfully.
