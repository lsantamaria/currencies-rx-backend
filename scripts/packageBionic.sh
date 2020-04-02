#!/usr/bin/env bash

set -x -eo pipefail

image_name="currencies-rx-backend:latest"

pack build $image_name --builder cnbs/sample-builder:bionic --path ../
