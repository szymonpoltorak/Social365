#!/bin/sh

docker compose -f ../compose.prod.yml --env-file=../services/.env up -d --build
