FROM openjdk:11-jdk
EXPOSE 8080
ARG JAR_FILE=build/libs/bank-0.0.4-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java", "-jar", "/docker-springboot.jar"]
