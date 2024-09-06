FROM openjdk:17

WORKDIR /chat-websocket-back-end

COPY target/chat-0.0.1-SNAPSHOT.jar chat-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "chat-0.0.1-SNAPSHOT.jar"]