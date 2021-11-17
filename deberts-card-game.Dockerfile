FROM maven:3.8.3-openjdk-17

# Create app-directory and copy the project
ADD . /deberts-card-game
WORKDIR /deberts-card-game

# Is everything ok?
RUN ls

# Run Maven build
RUN mvn clean install

# Remove the build container and just use the build artifact
FROM openjdk:17-jdk

LABEL maintainer="Ievgenii Izrailtenko"

VOLUME /tmp

# Add jar to container
COPY --from=0 "/deberts-card-game/backend/target/deberts-card-game.jar" deberts-card-game.jar

# Run jar-artifact
CMD ["java","-jar","/deberts-card-game.jar"]