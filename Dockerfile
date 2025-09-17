FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/basic_task_tracer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java","-jar","app.jar"]
