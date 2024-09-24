FROM amazoncorretto:17-alpine-jdk

ARG JAR_FILE=./build/libs/backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} review-me-app.jar

ENTRYPOINT ["java", "-jar", "/review-me-app.jar", "-Dspring.config.location=/application.yml"]
