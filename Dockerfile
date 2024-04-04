FROM openjdk:17-jdk-alpine
LABEL authors="tbaya"
ARG JAR_FILE=target/*.jar
COPY ./target/LagunaGym-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]