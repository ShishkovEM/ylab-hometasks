package hometask2.complexnumbers;

/**
 * Интерфейс для реализации операций с комплексными числами.
 * Имплементирован в классе ComplexNumberAlgebraic в алгебраической форме.
 * TODO: Имплементация в классе ComplexNumberExponential в показательной форме.
 */
public interface ComplexNumber {
    ComplexNumber add(ComplexNumber val); // Сложение чисел
    ComplexNumber subtract(ComplexNumber val); // Вычитание чисел
    ComplexNumber multiply(ComplexNumber val); // Умножение чисел
    ComplexNumber divide(ComplexNumber val); // Деление чисел
    double getAbs(); // Получение модуля
    double getReal(); // Получение действительной части
    double getImaginary(); // Получение мнимой части

}