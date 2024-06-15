# Используем образ Maven для сборки приложения
FROM maven:3.8.4-openjdk-17-slim AS builder

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
COPY --from=builder /build/target/test-zadanie-0.0.1-SNAPSHOT.jar /app/app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Команда для запуска приложения
CMD ["java", "-jar", "/app/app.jar"]
