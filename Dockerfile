FROM openjdk:11-jre-slim

COPY . /http-status

WORKDIR /http-status

EXPOSE 80 8080/tcp

ENTRYPOINT ["./gradlew", "run"]
