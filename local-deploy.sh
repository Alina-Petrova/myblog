#!/bin/sh
mvn clean package -P local && docker build --rm -t dhtssdev/myblog .
docker rm -f myblog || true && docker run -d -p 8080:8080 -p 4848:4848 --name myblog --network db dhtssdev/myblog  && docker logs -f myblog
