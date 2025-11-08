# --- ETAPA 1: Construcción (Build) ---
# Usamos una imagen de Maven (basada en Java 21) para construir el proyecto
FROM maven:3.9.7-eclipse-temurin-21 AS build

# Creamos un directorio de trabajo
WORKDIR /build

# Copiamos el pom.xml y descargamos las dependencias
# Esto crea una "capa" de caché para que no descargue todo cada vez
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos todo el código fuente y lo compilamos
# Saltamos los tests para que la build sea más rápida
COPY src ./src
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución (Run) ---
# Usamos la imagen base limpia (la que tú corregiste)
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos la Billetera (Wallet) de Oracle
# Esta ruta es del código fuente (contexto de build), por eso funciona
COPY src/main/resources/wallet ./config/wallet

# Copiamos SOLAMENTE el .jar que se creó en la ETAPA 1
COPY --from=build /build/target/levelup-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto que usa Spring Boot
ENV PORT=8080
EXPOSE 8080

# Comando para ejecutar el JAR, apuntando a la billetera
ENTRYPOINT ["java", "-Doracle.net.tns_admin=/app/config/wallet", "-jar", "/app/app.jar"]