FROM openjdk:8-jdk-alpine
ADD target/spring-demo1.jar demo1.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /demo1.jar