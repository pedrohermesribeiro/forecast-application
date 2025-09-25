FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY target/ai_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]