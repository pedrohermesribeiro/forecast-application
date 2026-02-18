# # ===== STAGE 1: BUILD =====
# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app

# COPY pom.xml .
# RUN mvn dependency:go-offline

# COPY src ./src

# RUN mvn clean package -DskipTests

# # ===== STAGE 2: RUNTIME =====
# FROM eclipse-temurin:21-jdk
# WORKDIR /app

# COPY --from=build /app/target/*SNAPSHOT.jar app.jar

# EXPOSE 8081
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]





# ======== STAGE 1: BUILD ========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ======== STAGE 2: FINAL ========
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/ai_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]

# Local
# FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
# WORKDIR /app
# COPY target/ai_service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8081
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]