#!/bin/sh


mvn clean package  -P prod && docker build --rm -t dhtssdev/myblog .

docker push dhtssdev/myblog