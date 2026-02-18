# ===== STAGE 1: BUILD =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]




# # Etapa 1 — Build com Maven
# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Etapa 2 — Imagem final
# FROM eclipse-temurin:21-jdk
# WORKDIR /app
# COPY --from=build /app/target/auth_service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8082
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8082"]
