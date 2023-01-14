#!/usr/bin/env bash
mvn clean install
docker build -t europe-west2-docker.pkg.dev/sabjicart-prod/dekh-buy-prod/dekh-buy-service-prod:M1.1 .
