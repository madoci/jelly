FROM ubuntu:14.04
COPY ./mathset /home/mathset
RUN apt-get -y update && apt-get install -y maven
WORKDIR /home/mathset
CMD mvn package
