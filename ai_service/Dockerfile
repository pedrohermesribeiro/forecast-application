FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/ai_service
COPY ai_service/pom.xml .
COPY ai_service/src ./src
RUN mvn clean package -DskipTests -f pom.xml

FROM eclipse-temurin:21-jdk
WORKDIR /app/ai_service
COPY --from=build /app/ai_service/target/ai_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]