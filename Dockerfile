# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime da aplicação
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/forecast-application-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
