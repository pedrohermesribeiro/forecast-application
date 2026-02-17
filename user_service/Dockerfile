# ======== STAGE 1: BUILD ========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ======== STAGE 2: FINAL ========
# Etapa final - runtime (sem build)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copia o .jar que já foi gerado na raiz pelo GitHub Actions
COPY user_service/target/user_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]




# FROM eclipse-temurin:21-jdk
# WORKDIR /app
# COPY --from=build /app/target/user_service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8083
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8083"]


# Local
# FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
# WORKDIR /app
# COPY target/user_service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8083
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8083"]