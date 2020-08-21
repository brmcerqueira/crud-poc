FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/crud-poc-1.0-fat.jar /app/crud-poc-1.0-fat.jar
WORKDIR /app

CMD ["java", "-jar", "crud-poc-1.0-fat.jar"]