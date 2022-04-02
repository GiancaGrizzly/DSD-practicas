service Calculadora {
    void ping(),
    double suma(1:double num1, 2:double num2),
    double resta(1:double num1, 2:double num2),
    double multiplicacion(1:double num1, 2:double num2),
    double division(1:double num1, 2:double num2),
    double exp(1:double num1, 2:double num2),
    double logaritmo(1:double num1, 2:double num2),
    double sumafile(1:list<double> sumandos),
}