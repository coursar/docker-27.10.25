FROM maven:3.9-eclipse-temurin-17@sha256:aaa8835df17b80a18a863456bc6ccd05e0ace19b92376a27ee0e6efd6bc10ec6 AS build

WORKDIR /opt/build

COPY pom.xml .
RUN mvn --no-transfer-progress --batch-mode dependency:go-offline
COPY src src
RUN mvn --offline -Dmaven.test.skip=true package

FROM tomcat:10.1.48-jdk17-temurin-noble@sha256:49c100b70d64a2169cb1df8292cef08904f7d5fdd5659e841a8c572ee3209274 AS release

WORKDIR /opt/app
COPY --from=build /opt/build/target/webapp-1.0.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080
