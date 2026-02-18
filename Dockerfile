# ======== STAGE 1: BUILD ========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia poms primeiro para cache de dependências
COPY pom.xml .
COPY ai_service/pom.xml ai_service/
COPY api_gateway/pom.xml api_gateway/
COPY auth_service/pom.xml auth_service/
COPY user_service/pom.xml user_service/

# Copia fontes (só o necessário, mas para segurança copia tudo)
COPY ai_service/src ai_service/src
COPY api_gateway/src api_gateway/src
COPY auth_service/src auth_service/src
COPY user_service/src user_service/src

# Build completo (todos módulos)
RUN mvn clean package -DskipTests -B

# ======== STAGE 2: RUNTIME (apenas para ai_service) ========
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copia SOMENTE o JAR do ai_service (ajuste o artifactId/versão se mudar)
COPY --from=build /app/ai_service/target/ai_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]
