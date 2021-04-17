FROM adoptopenjdk/openjdk12

EXPOSE 9999

ADD ./target/auth-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
