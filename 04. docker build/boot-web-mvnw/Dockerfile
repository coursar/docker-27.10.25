# To build selected target:
# docker build --progress plain --target deps -t webapp:1.0-deps .
FROM eclipse-temurin:21.0.9_10-jdk-noble@sha256:4ae3c996117de6f25f8544cc4b173d7ca1e4de49b4a535f8dd50b9cd38ee4804 AS deps
WORKDIR /opt/build
# anti-pattern -> (partial solution .dockerignore)
# COPY . .
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
#RUN ./mvnw --no-transfer-progress --batch-mode dependency:go-offline
RUN ./mvnw --no-transfer-progress --batch-mode org.apache.maven.plugins:maven-dependency-plugin:3.9.0:go-offline

# TODO: add hash
FROM webapp:1.0-deps AS build
COPY src src
RUN ./mvnw --offline -Dmaven.test.skip=true package

FROM eclipse-temurin:21.0.9_10-jdk-noble@sha256:4ae3c996117de6f25f8544cc4b173d7ca1e4de49b4a535f8dd50b9cd38ee4804 AS health
WORKDIR /opt/build
COPY health health 
# Health.class
RUN javac health/Health.java

FROM gcr.io/distroless/java21-debian12:nonroot@sha256:5fc28363e84dec4db09b7df71299879039639fa9c13db7c9d36ff61715e10859 AS release

WORKDIR /opt/app
# --from=stage
# --from=image
COPY --from=build /opt/build/target/boot-web-maven-0.0.1-SNAPSHOT.jar app.jar
COPY --from=health /opt/build/health/Health.class health/Health.class

HEALTHCHECK --start-period=30s CMD ["java", "health.Health"]

# USER 1000
EXPOSE 8080

CMD ["app.jar"]

