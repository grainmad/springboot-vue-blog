FROM openjdk:11.0.4-jre-slim-buster

RUN mkdir -p /blog/upload

COPY cms-1.0.0-SNAPSHOT.jar application.yml /blog/

WORKDIR /blog

ENTRYPOINT [ "java", "-jar", "cms-1.0.0-SNAPSHOT.jar" ]