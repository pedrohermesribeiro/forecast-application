FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
WORKDIR /app
COPY target/auth_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8082"]

