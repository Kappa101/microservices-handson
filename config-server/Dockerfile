FROM adoptopenjdk/openjdk12

EXPOSE 8888

ADD ./target/config-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]