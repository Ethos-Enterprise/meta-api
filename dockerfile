# Define a imagem base
FROM openjdk:17
WORKDIR /app
COPY target/meta-api-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "meta-api-0.0.1-SNAPSHOT.jar"]
