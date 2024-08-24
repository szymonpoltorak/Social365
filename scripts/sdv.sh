#!/bin/sh

docker compose -f ../compose.dev.yml --env-file=../services/.env down --rmi all --remove-orphans

docker image rm social:build

docker image rm social:prod
