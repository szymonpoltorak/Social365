#!/bin/sh

docker build -t social:build -f Dockerfile.build .

docker build -t social:prod -f Dockerfile.prod .

docker compose -f ../compose.prod.yml --env-file=../services/.env up -d --build
