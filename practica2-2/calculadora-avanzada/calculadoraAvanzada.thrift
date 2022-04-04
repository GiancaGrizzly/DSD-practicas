service CalculadoraAvanzada {
    void ping(),
    double exp(1:double num1, 2:double num2),
    double logaritmo(1:double num1, 2:double num2),
    double sumavector(1:list<double> sumandos),
}