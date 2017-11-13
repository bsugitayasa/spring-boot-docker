FROM openjdk:8-jdk-alpine
ADD target/spring-demo2.jar demo2.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /demo2.jar