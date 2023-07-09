FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY src/target/*.jar toolywood-0.0.1.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080