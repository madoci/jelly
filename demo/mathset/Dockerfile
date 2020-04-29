#
# Build stage
#
FROM maven:3.6-openjdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -B

#
# Package stage
#
FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/*.jar /home/demo.jar
WORKDIR /home
CMD java -jar demo.jar
