FROM adoptopenjdk/openjdk11:ubi

MAINTAINER qiren.chen

WORKDIR /opt/ichat
RUN mkdir /var/log/ichat
RUN chmod 777 /var/log/ichat
VOLUME /tmp

COPY ./target/chat-0.0.1-SNAPSHOT.jar chat.jar

EXPOSE 8080

ENTRYPOINT java -jar chat.jar