import glob
import sys

from calculadora import Calculadora

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
