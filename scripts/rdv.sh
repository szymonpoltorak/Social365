#!/bin/sh

docker compose -f ../compose.dev.yml --env-file=../services/.env up -d --build
