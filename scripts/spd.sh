#!/bin/sh

docker compose -f ../compose.prod.yml --env-file=../services/.env down --rmi all --remove-orphans
