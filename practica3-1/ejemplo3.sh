#!/bin/sh -e
# ejecutar = Macro para compilación y ejecución del programa completo
# en una sola máquina Unix de nombre localhost

echo
echo "Compilando con javac ..."
javac ejemplo3/*.java

sleep 2

echo
echo "Lanzando el servidor"
java -cp .  -Djava.rmi.server.codebase=file:./ejemplo3/ \
            -Djava.rmi.server.hostname=localhost \
            -Djava.security.policy=ejemplo3/server.policy \
            ejemplo3.servidor &

sleep 2

echo
echo "Lanzando el cliente"
echo
java -cp .  -Djava.security.policy=ejemplo3/server.policy \
            ejemplo3.cliente