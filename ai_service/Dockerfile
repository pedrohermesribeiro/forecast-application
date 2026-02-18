# ======== STAGE 1: BUILD ========
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml do módulo primeiro → permite cache inteligente das dependências
COPY pom.xml .

# Baixa dependências (ótimo para acelerar builds subsequentes)
RUN mvn dependency:go-offline -B

# Agora copia o código fonte
COPY src ./src

# Compila e empacota (sem testes para agilizar o deploy)
RUN mvn clean package -DskipTests -B

# ======== STAGE 2: FINAL (runtime leve) ========
FROM eclipse-temurin:21-jdk-alpine   
WORKDIR /app

# Copia o JAR gerado – use wildcard para evitar erro se versão mudar
COPY --from=build /app/target/*-0.0.1-SNAPSHOT.jar app.jar

# Porta que o serviço escuta (ajuste se necessário)
EXPOSE 8081

# Inicia com porta explícita (útil se o application.properties tiver server.port diferente)
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]

# Local
# FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
# WORKDIR /app
# COPY target/ai_service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8081
# ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]