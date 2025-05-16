# Utilizamos una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Se define el directorio de la aplicación dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR al contenedor
COPY target/IceCream-SpringBoot-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que corre la aplicación (ajusta si es distinto)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
