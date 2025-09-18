FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Dá permissão de execução ao mvnw
RUN chmod +x mvnw

# Executa o build da aplicação
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagem final
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/forecast-application-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 80 (requisito do Azure App Service)
EXPOSE 80

# Força o Spring Boot a rodar na porta 80
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=80"]

