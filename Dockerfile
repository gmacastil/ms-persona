FROM eclipse-temurin:21-alpine-3.24

WORKDIR /app

COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
