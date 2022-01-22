#docker build . -f deberts-game.Dockerfile -t deberts-game --no-cache --progress=plain --network="host"
#FROM python:3.7-alpine

#RUN ping localhost -c 4
#RUN ping deberts-db -c 4

#FROM ubuntu:18.04
#RUN echo "try to install curl"
#RUN apt-get update -y
#RUN apt install curl -y
#RUN echo "try to call localhost"
#RUN curl localhost:5432
#RUN echo "try to call deberts-db"
#RUN curl deberts-db:5432

FROM maven:3.8.3-openjdk-17

# Create app-directory and copy the project
ADD . /deberts-card-game
WORKDIR /deberts-card-game

# Is everything ok?
RUN ls


# Run Maven build
RUN mvn clean install

RUN echo "jar is ready"
# Remove the build container and just use the build artifact
FROM openjdk:17-jdk

LABEL maintainer="Ievgenii Izrailtenko"

VOLUME /tmp

# Add jar to container
COPY --from=0 "/deberts-card-game/backend/target/deberts-card-game.jar" deberts-card-game.jar

# Run jar-artifact
CMD ["java","-jar","/deberts-card-game.jar"]