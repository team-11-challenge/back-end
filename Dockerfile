FROM openjdk:17
ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar
VOLUME ["/var/logs"]
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

