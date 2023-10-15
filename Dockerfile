FROM openjdk:17

# Diretório de trabalho no contêiner
WORKDIR /votacao

# Copie o arquivo JAR da sua aplicação para o contêiner
COPY target/votacao-1.0.0.jar /votacao/votacao.jar

# Comando para iniciar a aplicação
CMD ["java", "-jar", "votacao.jar"]
