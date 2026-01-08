FROM eclipse-temurin:21-jre-alpine
MAINTAINER Duong Bui <duongbq83@gmail.com>
VOLUME /app
EXPOSE 8080
ADD ./build/libs/*.jar app.jar
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ENTRYPOINT ["java","-jar","/app.jar"]
