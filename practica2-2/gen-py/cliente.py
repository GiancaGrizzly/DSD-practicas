from ast import operator
import sys

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

print(len(sys.argv)-1)

numero_1 = float(sys.argv[1])
operator = sys.argv[2]
numero_2 = float(sys.argv[3])

print(str(numero_1) + " " + str(operator) + " " + str(numero_2))

if (operator == "+"):
    resultado = client.suma(numero_1, numero_2)
elif (operator == "-"):
    resultado = client.resta(numero_1, numero_2)
elif (operator == "x"):
    resultado = client.multiplicacion(numero_1, numero_2)
elif (operator == "/"):
    resultado = client.division(numero_1, numero_2)

if "resultado" in locals():
    print(str(numero_1) + " " + str(operator) + " " + str(numero_2) + " = " + format(resultado,'.4f'))

transport.close()
