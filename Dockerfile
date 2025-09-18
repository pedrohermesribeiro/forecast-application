# Etapa 1: build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/forecast-application-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 80 (para Azure App Service)
EXPOSE 80

# Força o Spring Boot a rodar na porta 80
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=80"]


