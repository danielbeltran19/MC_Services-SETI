# ===== Etapa 1: Compilar el proyecto =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Empaquetar (sin correr tests)
RUN mvn clean package -DskipTests

# ===== Etapa 2: Imagen final ligera =====
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el .jar desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
