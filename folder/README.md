# Prerquisitos

Insalar el JDK 21

https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html


# Compilar

./gradlew build

# Ejecutar

La aplicación se conecta a MySQL (`localhost:3306`, base `persona`, que se crea
si no existe). Ajusta la conexión en `src/main/resources/application.yaml`.
Hibernate actualiza automáticamente la tabla `personas`.

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
