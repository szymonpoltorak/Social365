#!/bin/sh

docker build -t social-native:base .

docker compose -f ../compose.dev.yml --env-file=../services/.env up -d --build
