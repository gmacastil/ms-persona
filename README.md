# Prerquisitos

Insalar el JDK 21

https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html


# Compilar

./gradlew build

# Ejecutar

java -jar build/libs/ms-persona-0.0.1-SNAPSHOT.jar

# enviar a sonaqube

Configura `SONAR_TOKEN` con un token de análisis autorizado para el proyecto. En Bash, puedes introducirlo sin mostrarlo ni incluirlo en el historial:

```bash
read -rsp "Token SonarQube: " SONAR_TOKEN
echo
export SONAR_TOKEN
```

Luego ejecuta:

./gradlew sonar \
  -Dsonar.host.url=https://sonarqube.business-litethinking.com \
  -Dsonar.projectKey=ms-persona
