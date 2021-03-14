FROM gradle:jdk11 as build
LABEL stage=intermediate

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ["gradle", "build", "--no-daemon", "-Pprod"]

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.8.0/wait /wait
RUN chmod +x /wait

COPY --from=build /home/gradle/src/build/libs/*.jar MonitorApp.jar
EXPOSE 8080
ENTRYPOINT ["/wait"]
RUN ["java", "-jar", "-Dspring.profiles.active=prod", "MonitorApp.jar"]