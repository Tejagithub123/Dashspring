
FROM openjdk:17-jdk-slim


WORKDIR /app


COPY ./target/User-0.0.1-SNAPSHOT.jar sample-app.jar


EXPOSE 8090


ENTRYPOINT ["java", "-jar", "/app/sample-app.jar"]
