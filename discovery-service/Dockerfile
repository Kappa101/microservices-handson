######################################################
#
#  Eureka Discovery Server
#  spring-cloud.version = Hoxton.SR6
#  Add this Dockefile path to docker-compose to use it
#  when running Junit , no need of euereka server
#   use annotations specific to disabling eureka
#
#
#
#
######################################################

FROM adoptopenjdk/openjdk12

EXPOSE 8761

ADD ./target/discovery-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]