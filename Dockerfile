FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/telegramBot.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/telegramBot.jar
#WORKDIR /opt/app
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","-Dserver.port=$PORT","app.jar"]