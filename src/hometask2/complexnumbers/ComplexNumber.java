package hometask2.complexnumbers;

/**
 * Интерфейс для реализации операций с комплексными числами.
 * Имплементирован в классе ComplexNumberAlgebraic в алгебраической форме.
 * TODO: Имплементация в классе ComplexNumberExponential в показательной форме.
 */
public interface ComplexNumber {

    ComplexNumber add(ComplexNumber val);

    ComplexNumber subtract(ComplexNumber val);

    ComplexNumber multiply(ComplexNumber val);

    ComplexNumber divide(ComplexNumber val);

    double getAbs();

    double getReal();

    double getImaginary();

}