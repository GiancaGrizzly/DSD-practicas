#!/bin/sh -e
# ejecutar = Macro para compilación y ejecución del programa completo
# en una sola máquina Unix de nombre localhost

echo
echo "Lanzando el ligador de RMI ..."
rmiregistry &

echo
echo "Compilando con javac ..."
javac ejemplo1/*.java

sleep 2

echo
echo "Lanzando el servidor"
java -cp .  -Djava.rmi.server.codebase=file:./ejemplo1/ \
            -Djava.rmi.server.hostname=localhost \
            -Djava.security.policy=ejemplo1/server.policy \
            ejemplo1.Ejemplo &

sleep 2

echo
echo "Lanzando el primer cliente"
echo
java -cp .  -Djava.security.policy=ejemplo1/server.policy \
            ejemplo1.Cliente_Ejemplo localhost 0

sleep 2

echo
echo "Lanzando el segundo cliente"
echo
java -cp .  -Djava.security.policy=ejemplo1/server.policy \
            ejemplo1.Cliente_Ejemplo localhost 3