# Imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Directorio dentro del contenedor
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Dar permisos al mvnw (IMPORTANTE en Linux)
RUN chmod +x mvnw

# Construir el proyecto
RUN ./mvnw clean package -DskipTests

# Exponer puerto 8080
EXPOSE 8080

# Ejecutar el jar generado
CMD ["java","-jar","target/parqueadero-0.0.1-SNAPSHOT.jar"]
