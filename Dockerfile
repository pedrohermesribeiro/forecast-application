# Etapa 1: build
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
