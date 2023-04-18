FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/stock-0.0.1-SNAPSHOT.jar stock.jar
ENTRYPOINT ["java","-jar","/stock.jar"]