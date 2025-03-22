#!/bin/bash
# wait-for-it.sh

set -e

host="$1"
shift
cmd="$@"

until curl -s "http://$host/health" > /dev/null || curl -s "http://$host/realms/master" > /dev/null; do
  >&2 echo "Keycloak is unavailable - sleeping"
  sleep 5
done

>&2 echo "Keycloak is up - executing command"
exec $cmd