from ast import operator
import sys
from unittest import result

from calculadora import Calculadora

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

transport = TSocket.TSocket("localhost", 9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Calculadora.Client(protocol)

transport.open()

# ----------------------------------------------------------

print("Hacemos ping al server")
client.ping()


if (len(sys.argv) == 4):

    # Comprobación de errores en los argumentos
    operator = sys.argv[2]

    try:
        numero_1 = float(sys.argv[1])
    except ValueError:
        print("ERROR: argumento no válido -- " + sys.argv[1])
        exit(-1)

    try:
        numero_2 = float(sys.argv[3])
    except ValueError:
        print("ERROR: argumento no válido -- " + sys.argv[3])
        exit(-1)
        

    if (operator == "+"):
        resultado = client.suma(numero_1, numero_2)
    elif (operator == "-"):
        resultado = client.resta(numero_1, numero_2)
    elif (operator == "x"):
        resultado = client.multiplicacion(numero_1, numero_2)
    elif (operator == "/"):
        resultado = client.division(numero_1, numero_2)
    elif (operator == "exp"):
        resultado = client.exp(numero_1, numero_2)
    elif (operator == "log"):
        resultado = client.logaritmo(numero_1, numero_2)

    if "resultado" in locals():
        print(str(numero_1) + " " + str(operator) + " " + str(numero_2) + " = " + format(resultado,'.4f'))

else:

    operator = sys.argv[1]

    if (operator == "sumavector"):

        sumandos = []

        for i in range(2, len(sys.argv)):

            try:
                arg = float(sys.argv[i])
            except ValueError:
                print("ERROR: argumento no válido -- " + sys.argv[i])
                exit(-1)

            sumandos.append(arg)
        
        if (len(sumandos) > 0):
            resultado = client.sumavector(sumandos)

            print("Sumandos: " + str(sumandos))
            print("Resultado: " + format(resultado, '.4f'))


transport.close()
