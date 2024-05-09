#!/usr/bin/env sh

CQL="CREATE KEYSPACE IF NOT EXISTS posts_comments WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};"

until echo ${CQL} | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /usr/local/bin/docker-entrypoint.sh "$@"
