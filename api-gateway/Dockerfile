# Используем образ Maven для сборки приложения
FROM maven:3.9.8-eclipse-temurin-17-alpine AS builder

# Устанавливаем рабочую директорию для сборки
WORKDIR /build

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Кэшируем зависимости Maven
COPY src/ /build/src/
RUN mvn package -DskipTests

# Используем образ OpenJDK для запуска приложения
FROM openjdk:17-oracle

# Устанавливаем рабочую директорию для приложения
WORKDIR /app

# Копируем собранный JAR файл из стадии сборки
COPY --from=builder /build/target/*.jar /app/app.jar

# Открываем порт 8080 для приложения
EXPOSE 8765

# Команда для запуска приложения
CMD ["java", "-jar", "/app/app.jar"]
