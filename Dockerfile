# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de tu aplicación
COPY target/wedding-backend-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]