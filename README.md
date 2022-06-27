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
docker run   --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db 
docker build --network="host" . -f deberts-game.Dockerfile -t deberts-game --no-cache --progress=plain
```

#### In Docker-network

You need to create a network for DNS container-names

```
docker network create javagath
docker run --net javagath --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db
docker run --net javagath  --name deberts-game -p 8080:8080 -d deberts-game 
docker network inspect javagath
```

#### In Kubernetes

```
kubectl apply -f deberts-db.yaml  
kubectl apply -f deberts-game.yaml  
kubectl get pods -o wide  
```

### Keystore

To generate Keystore with private key:

```
keytool -genkey -alias debertskey -keyalg RSA -keystore deberts.jks -keysize 2048
```

<br>CN=Ievgenii Izrailtenko
<br>OU=JavaGath
<br>O=JavaGathOrg
<br>L=Erkrath
<br>ST=NRW
<br>C=DE
<br>Password=deberts123

After that we have to generate a certificate for the public key

#### HTTPS

We need to generate a keypair

```
keytool -genkeypair -alias debertskeypair -keyalg RSA -keysize 4096 -storetype JKS -keystore deberts.jks -validity 3650
```

After that we can use it to enable https

### Actual ToDo's:

1. ~~Run project using docker~~
2. ~~Create deck model~~
3. ~~Infrastructure~~
4. ~~Game model~~
5. ~~Deal cads~~
6. ~~Trade Phase~~
7. ~~Combination Phase~~
8. ~~Action Phase~~
9. ~~Round sum up~~
10. ~~Party~~
11. ~~MVN-Infrastructure Frontend~~
12. ~~Security~~
13. DB-Schema
14. Save/Load
15. Frontend
    1. Vue Part
    2. Rest Controller

### Useful topics:

- Testing
    - Test ---> CardTest.java
    - ParameterizedTest CSV ---> CardTest.java
    - ParameterizedTest EnumSource ---> CardTest.java
    - ControllerTest Get ---> DefaultControllerTest.java
    - ControllerTest Get with Headers ---> DefaultControllerTest.java
    - ControllerTest Post ---> AuthenticationControllerTest.java
    - Test Postgres via H2 ---> UserServiceTest.java + application-unittest.properties
- Security
    - JWT Creation, Validation etc ---> JwtService.java
    - JWT Security Config (whitelisting, stateless, without basic auth) ---> WebSecurity.java
    - JWT Authentication ---> JwtTokenFilter.java
    - HistoryMode for embedded tomcat ---> HistoryModeFilter.java
    - Store Passwords in DB using BCrypt, Salt and Pepper ---> UserService,java
    - Redirect to login-page using exception ---> WebSecurity.java (
      LoginUrlAuthenticationEntryPoint)
- General
    - MVN + Vue ---> pom.xml
    - State Pattern ---> Phase.java, Action.java, Combo.java, Trade.java

      [![Anurag's GitHub stats](https://github-readme-stats.vercel.app/api?username=javagath)](https://github.com/anuraghazra/github-readme-stats)