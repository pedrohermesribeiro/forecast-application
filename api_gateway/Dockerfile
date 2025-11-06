# ======== STAGE 1: BUILD ========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/api_gateway

# Copia apenas o pom.xml
COPY pom.xml .
COPY src ./src

# Builda o JAR
RUN mvn clean package -DskipTests

# ======== STAGE 2: FINAL ========
FROM eclipse-temurin:21-jdk
WORKDIR /app/api_gateway

# Copia o JAR gerado pelo Maven
COPY --from=build /app/api_gateway/target/api_gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
