#!/bin/sh -e
# ejecutar = Macro para compilación y ejecución del programa completo
# en una sola máquina Unix de nombre localhost

echo
echo "Lanzando el ligador de RMI ..."
rmiregistry &

echo
echo "Compilando con javac ..."
javac ejemplo2/*.java

sleep 2

echo
echo "Lanzando el servidor"
java -cp .  -Djava.rmi.server.codebase=file:./ejemplo2/ \
            -Djava.rmi.server.hostname=localhost \
            -Djava.security.policy=ejemplo2/server.policy \
            ejemplo2.Ejemplo &

sleep 2

echo
echo "Lanzando el cliente y sus hebras"
echo
java -cp .  -Djava.security.policy=ejemplo2/server.policy \
            ejemplo2.Cliente_Ejemplo localhost 11