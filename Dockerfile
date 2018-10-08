FROM openjdk:11-jre-slim

COPY ./build/install/http-status /http-status

# RUN apk add --no-cache bash

EXPOSE 80 4567/tcp

ENTRYPOINT ["/http-status/bin/http-status"]
