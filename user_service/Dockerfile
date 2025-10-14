FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/user_service
COPY user_service/pom.xml .
COPY user_service/src ./src
RUN mvn clean package -DskipTests -f pom.xml

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/user_service/target/user_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]