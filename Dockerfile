FROM openjdk:21-slim
LABEL authors="kirleon"
WORKDIR /app
COPY target/
ENTRYPOINT ["top", "-b"]