#!/usr/bin/env bash

./gradlew downloadApolloSchema --endpoint="https://api.github.com/graphql" --schema="app/src/main/graphql/io/github/obuiron/sampleapp/schema.json" --header="Authorization: Bearer $GITHUB_TOKEN"
