# 1. Imagen base con Java 21 (coincide con tu pom.xml)
FROM openjdk:21-jdk-slim

# 2. Establecer un directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiar la Billetera (Wallet) de Oracle
# Copiamos la carpeta de la billetera que está en 'src/main/resources'
# a una nueva carpeta 'config/wallet' dentro del contenedor
COPY src/main/resources/wallet ./config/wallet

# 4. Copiar el archivo .jar generado por Maven
# El nombre levelup-0.0.1-SNAPSHOT.jar es correcto según tu pom.xml
COPY target/levelup-0.0.1-SNAPSHOT.jar app.jar

# 5. Render usa la variable PORT, la exponemos
ENV PORT=8080
EXPOSE 8080

# 6. Comando para ejecutar el JAR
# IMPORTANTE: Usamos -Doracle.net.tns_admin para decirle a Java
# dónde encontrar la billetera que copiamos en el paso 3.
ENTRYPOINT ["java", "-Doracle.net.tns_admin=/app/config/wallet", "-jar", "/app/app.jar"]