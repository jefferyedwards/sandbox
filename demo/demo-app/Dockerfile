FROM openjdk:8-jre
MAINTAINER Jeff Edwards <jeff@edwardsonthe.net>

COPY target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar

EXPOSE 8080

VOLUME /tmp

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/usr/local/lib/app.jar"]
