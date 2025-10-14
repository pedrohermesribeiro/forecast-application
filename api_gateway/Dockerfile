FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY api_gateway/pom.xml .
COPY api_gateway/src ./src
RUN mvn clean package -DskipTests -f pom.xml

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/auth_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]