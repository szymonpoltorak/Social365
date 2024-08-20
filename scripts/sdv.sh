#!/bin/sh

docker compose -f ../compose.dev.yml --env-file=../services/.env down --rmi all --remove-orphans
