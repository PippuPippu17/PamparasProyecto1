# Dockerfile para PumaBank - Sistema Bancario Digital
# Utiliza Java 21 (OpenJDK) y Maven para compilar y ejecutar

# Etapa 1: Construcción
FROM maven:3.9-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (se cachea esta capa)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src
COPY test ./test

# Compilar el proyecto y ejecutar tests
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre

# Metadata del contenedor
LABEL maintainer="LasPamparas"
LABEL description="PumaBank - Sistema Bancario Digital con 8 patrones de diseño"
LABEL version="1.0"

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la etapa de construcción
COPY --from=build /app/target/pumabank-1.0-SNAPSHOT.jar /app/pumabank.jar

# Copiar recursos adicionales si existen
COPY --from=build /app/target/classes /app/classes

# Exponer puerto si se requiere (opcional para futuras extensiones)
# EXPOSE 8080

# Variable de entorno para configuración
ENV JAVA_OPTS=""

# Comando por defecto: ejecutar la aplicación con GUI
# Nota: Para ejecutar con GUI en Docker, se necesita X11 forwarding
CMD ["java", "-cp", "/app/pumabank.jar", "main.Main"]

# Para ejecutar sin GUI (modo consola), usar:
# CMD ["java", "-cp", "/app/pumabank.jar", "main.Main"]
