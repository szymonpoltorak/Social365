#!/bin/sh

docker build -t social-native:base .

docker compose -f ../compose.prod.yml --env-file=../services/.env up -d --build
