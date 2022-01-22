# docker build . -f deberts-db.Dockerfile -t deberts-db
# docker build --net javagath . -f deberts-game.Dockerfile -t deberts-game
# docker build . -f deberts-game.Dockerfile -t deberts-game
# docker run --net javagath --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db
# docker run --network="host" --name deberts-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d deberts-db
## docker network create javagath-network
## --net javagath-network
## docker exec -ti 6621dae1ad59 bash
# apt update
# apt install curl
# curl deberts-db:5432
# docker network connect javagath deberts-db
FROM postgres:14.1
LABEL maintainer="Ievgenii Izrailtenko"
ENV LANG en_US.utf-8
COPY init.sql /docker-entrypoint-initdb.d/

# docker build --network=javagath . -f deberts-game.Dockerfile -t deberts-game --no-cache --progress=plain
