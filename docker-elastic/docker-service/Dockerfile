FROM openjdk:11-jre
MAINTAINER Jeff Edwards <jeff@edwardsonthe.net>

VOLUME /tmp

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

RUN mkdir /app/logs && \
    groupadd --gid 1000 npe &&  \
    adduser --uid 1000 --gid 1000 --no-create-home npe && \
    usermod --lock npe && \
    chown -R npe. /app

USER 1000

ENTRYPOINT ["java", "-cp","app:app/lib/*", "-Dspring.profiles.active=docker", "com.boeing.demo.Application"]
