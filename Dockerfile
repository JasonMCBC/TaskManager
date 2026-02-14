# Etapa 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de Gradle
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Descargar dependencias (cacheable)
RUN ./gradlew dependencies --no-daemon || true

# Copiar código fuente
COPY src src

# Compilar (sin tests)
RUN ./gradlew clean build -x test --no-daemon

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]