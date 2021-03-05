FROM openjdk:11.0-slim

WORKDIR /app
COPY build/libs/MonitorApp-0.0.1.jar MonitorApp.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "MonitorApp.jar" ]