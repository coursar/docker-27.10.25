FROM maven:3.9.11-eclipse-temurin-21-noble AS build
WORKDIR /app
COPY . .
RUN mvn -B package

FROM tomcat:10.1.49-jdk21-temurin-noble AS release
COPY --from=build /app/target/webservice-1.0.war $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080
