import glob
import numbers
import sys

import math

from calculadoraAvanzada import CalculadoraAvanzada

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import logging
logging.basicConfig(level=logging.DEBUG)

# ----------------------------------------------------------

class CalculadoraAvanzadaHandler:

    def __init__(self):
        self.log = {}

    def ping(self):
        print("Me han hecho ping()")

    def exp(self, n1, n2):
        print("Elevando " + str(n1) + " a " + str(n2))
        return n1 ** n2

    def logaritmo(self, n1, n2):
        print("Calculando el logaritmo de " + str(n1) + " en base " + str(n2))
        return math.log(n1, n2)

    def sumafile(self, sumandos):
        print("Sumando los números proporcionados en el archivo")
        result = 0
        for number in sumandos:
            result += number
        return result

# ----------------------------------------------------------

if __name__ == "__main__":
    handler = CalculadoraAvanzadaHandler()
    processor = CalculadoraAvanzada.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port=9091)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("Iniciando servidor avanzado...")
    server.serve()
    print("Fin")
