FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Configurar encoding UTF-8
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

# Copiar o arquivo pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
COPY . .

# Instalar Maven e construir o projeto
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests -Dfile.encoding=UTF-8 && \
    apk del maven

# Expor a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
