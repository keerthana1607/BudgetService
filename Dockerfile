FROM openjdk:18
WORKDIR /app
COPY ./target/tap-api.jar /app
EXPOSE 8081
CMD ["java", "-jar", "tap-api.jar"]
