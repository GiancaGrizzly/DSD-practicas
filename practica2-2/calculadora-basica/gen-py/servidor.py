from ast import operator
import glob
import sys

from calculadora import Calculadora

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import logging
logging.basicConfig(level=logging.DEBUG)

# ----------------------------------------------------------

class CalculadoraHandler:

    def __init__(self):
        self.log = {}

        # Configurar el servidor Calculadora Básica como cliente de Calculadora Avanzada
        transport = TSocket.TSocket("localhost", 9091)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)

        self.client = Calculadora.Client(protocol)

        transport.open()

    def ping(self):
        print("Me han hecho ping()")

    def suma(self, n1, n2):
        print("Sumando " + str(n1) + " con " + str(n2))
        return n1 + n2

    def resta(self, n1, n2):
        print("Restando " + str(n1) + " con " + str(n2))
        return n1 - n2
    
    def multiplicacion(self, n1, n2):
        print("Multiplicando " + str(n1) + " por " + str(n2))
        return n1 * n2
    
    def division(self, n1, n2):
        print("Diviendo " + str(n1) + " por " + str(n2))
        return n1 / n2
    
    def exp(self, n1, n2):
        print("Delegando el cálculo de " + str(n1) + " elevado a " + str(n2))
        return self.client.exp(n1,n2)

    def logaritmo(self, n1, n2):
        print("Delegando el cálculo del logaritmo de " + str(n1) + " en base " + str(n2))
        return self.client.logaritmo(n1,n2)

    def sumavector(self, sumandos):
        print("Delegando el cálculo de la suma de los números del archivo")
        return self.client.sumavector(sumandos)

# ----------------------------------------------------------

if __name__ == "__main__":
    handler = CalculadoraHandler()
    processor = Calculadora.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("Iniciando servidor...")
    server.serve()
    print("Fin")
