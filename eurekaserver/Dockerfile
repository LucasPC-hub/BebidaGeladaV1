# Use a imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o contêiner
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação Spring Boot usará
EXPOSE 8761

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]