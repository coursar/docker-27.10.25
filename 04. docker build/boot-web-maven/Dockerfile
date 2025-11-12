FROM maven:3.9.11-eclipse-temurin-25-alpine@sha256:21380f23ee1e9332ef51834ae62c0084fbad3283191507a97dece16e8ed05c2f AS build

WORKDIR /opt/build

COPY pom.xml .
RUN mvn --no-transfer-progress --batch-mode dependency:go-offline
COPY src src
RUN mvn -B package

FROM eclipse-temurin:21.0.9_10-jdk-noble@sha256:4ae3c996117de6f25f8544cc4b173d7ca1e4de49b4a535f8dd50b9cd38ee4804 AS health
WORKDIR /opt/build
COPY health health 
# Health.class
RUN javac health/Health.java

FROM eclipse-temurin:21.0.9_10-jre-noble@sha256:f4751a2c191d65c022bc0b364c52672db38de8728e5e194db4bb820d54ef0b6f AS release

WORKDIR /opt/app
COPY --from=health /opt/build/health/Health.class health/Health.class
COPY --from=build /opt/build/target/boot-web-maven-0.0.1-SNAPSHOT.jar app.jar

HEALTHCHECK --start-period=30s CMD ["java", "health.Health"]

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
