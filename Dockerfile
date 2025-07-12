FROM openjdk:21

LABEL maintainer="cjxyl@foxmail.com"

ADD build/libs/LocalApplication-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]