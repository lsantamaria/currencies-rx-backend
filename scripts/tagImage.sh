#!/usr/bin/env bash

set -eo pipefail

if [[ -z "$1" ]]; then
  echo "tag version required. Usage: tagImage.sh <version>"
  exit 1
fi

TAG=$1
APP_NAME="currencies-rx-backend"
DOCKER_REPO="lsantamaria"

docker tag "$APP_NAME:latest" "$DOCKER_REPO/$APP_NAME:$TAG"

docker push "$DOCKER_REPO/$APP_NAME:$TAG"
