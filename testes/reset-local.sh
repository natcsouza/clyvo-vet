#!/bin/bash
# Zera o banco local e sobe o SOLIN do zero (dados de teste controlados).
pkill -f "^/usr/lib/jvm/java-21-openjdk/bin/java -jar target/solin-api"; sleep 2
docker rm -f solin-db >/dev/null 2>&1
docker run -d --name solin-db -e POSTGRES_DB=solindb -e POSTGRES_USER=solin -e POSTGRES_PASSWORD=solin123 -p 127.0.0.1:5433:5432 postgres:16-alpine >/dev/null
sleep 4
cd "$(dirname "$0")/.." && DB_URL=jdbc:postgresql://localhost:5433/solindb nohup /usr/lib/jvm/java-21-openjdk/bin/java -jar target/solin-api-0.0.1-SNAPSHOT.jar > /tmp/solin.log 2>&1 &
for i in $(seq 1 40); do curl -s -o /dev/null -w "%{http_code}" localhost:8080/solin/login | grep -q 200 && { echo "SOLIN no ar"; exit 0; }; sleep 3; done; echo "falhou"; exit 1
