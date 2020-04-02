#!/usr/bin/env bash

set -eo pipefail

if [[ -z "$1" ]]; then
  echo "builder-name required. Usage: createBuilder <builder-name>"
  exit 1
fi

BUILDER_NAME=$1

pack create-builder "$BUILDER_NAME:bionic" --builder-config ../builders/builder.toml
