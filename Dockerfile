FROM ubuntu:20.04

RUN apt-get update
RUN apt-get install sudo
RUN sudo apt-get install -y git
RUN sudo apt-get install -y vim

RUN sudo apt-get install -y default-jdk

RUN sudo apt-get install maven

RUN mkdir /root/project

WORKDIR /root/project

COPY ./run.sh /root/project

CMD ["/root/project/run.sh"] 