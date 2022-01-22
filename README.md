# deberts card game

This project is the own implementation of popular card game in CIS states.<br />

The main goal of this project is to improve my programming skills. In details:

- improve my skills in Java-stuff like Java language, Spring Boot and Maven
- learn docker
- learn JS and its Vue
- learn how to write in english :D

### Project setup

This project is built with parent maven which contains backend and frontend parts.

```
deberts
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

### How to build

#### Locally

Firs of all you have to simulate host names on your localhost

```
sudo vim /etc/hosts
```

After that you can start to create your containers. It is important to use host-network.
<br> While building the process is not in the docker-network because it is not a docker container.
<br> With host-network Dockerfile will use while building host-names from your machine.

```
docker build . -f deberts-db.Dockerfile -t deberts-db 
docker run   --network="host" --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db 
docker build --network="host" . -f deberts-game.Dockerfile -t deberts-game --no-cache --progress=plain
```

#### In Docker-network

You need to create a network for DNS container-names

```
docker network create javagath
docker run --net javagath --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db
docker run --net javagath  --name deberts-game -p 8080:8080 -d deberts-game 
docker network inspectr javagath
```

#### In Kubernetes

```
kubectl apply -f deberts-db.yaml  
kubectl apply -f deberts-game.yaml  
kubectl get pods -o wide  
```

### Actual ToDo's:

1. ~~Run project using docker~~
2. ~~Create deck model~~
3. Infrastructure
    1. ~~Separate containers for jar and db~~
    2. ~~Connection to db~~
    3. Kubernetes' infrastructure for Java-App and DB
    4. Volume for db
    5. Good practices for docker and kubernetes
4. Game model
    1. Player
    2. Round
    3. Party
5. Deal cads
    1. First dealer appear to be chosen randomly
    2. Next dealer is a winner of the last round
    3. Each player gets 6 cards
    4. Trump generation
6. DB-Schema
7. Trade Phase
8. Combination Phase
9. Action Phase