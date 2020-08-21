FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/crud-poc-1.0-fat.jar /app/crud-poc-1.0-fat.jar
WORKDIR /app

CMD ["java",
"-server",
"-XX:+UnlockExperimentalVMOptions",
"-XX:+UseCGroupMemoryLimitForHeap",
"-XX:InitialRAMFraction=2",
"-XX:MinRAMFraction=2",
"-XX:MaxRAMFraction=2",
"-XX:+UseG1GC",
"-XX:MaxGCPauseMillis=100",
"-XX:+UseStringDeduplication",
"-jar",
"crud-poc-1.0-fat.jar"]