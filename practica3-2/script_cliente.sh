#!/bin/sh -e

echo
echo "Lanzando el cliente"
echo
java -cp .  -Djava.security.policy=server.policy \
            Cliente 2