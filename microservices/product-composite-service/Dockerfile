FROM adoptopenjdk/openjdk12

EXPOSE 8080

#RUN mvn clean package -DskipTests=true ##not in place as base image has no maven
ADD ./target/product-composite-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

######################################################
#
#  why jdk12 ?
#  till jdk9 - Java causing issues in Docker container
#  JVM was using underlying docker host's CPU cores
#  and memory even after Isolation
#  JDK10+
#  Java behaves and adheres to docker properly
#  note: JVM allocates itself 1/4th of physical memory
#         to heap memory
######################################################

