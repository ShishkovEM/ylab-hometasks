package hometask2.complexnumbers;

/**
 * Реализовать класс, описывающий комплексное число (действительная и мнимая часть
 * должны иметь точность double). Должны быть доступны следующие операции:
 * 1. Создание нового числа по действительной части (конструктор с 1
 * параметром)
 * 2. Создание нового числа по действительной и мнимой части (конструктор
 * с 2 параметрами)
 * 3. Сложение
 * 4. Вычитание
 * 5. Умножение
 * 6. Операция получения модуля
 * 7. преобразование в строку (toString)
 * (арифметические действия должны создавать новый экземпляр класса)
 */
public class ComplexNumberAlgebraic implements ComplexNumber {
    private final double real; // Действительная часть комплексного числа в алгебраической форме
    private final double imaginary; // Мнимая часть комплексного числа в алгебраической форме

    /**
     * Создание нового числа по действительной части (конструктор с 1 параметром)
     * @param real - действительная часть мнимого числа в алгебраической форме
     */
    public ComplexNumberAlgebraic(double real) {
        this.real = real;
        this.imaginary = 0.0;
    }

    /**
     * Создание нового числа по действительной и мнимой части (конструктор с 2 параметрами)
     * @param real - действительная часть мнимого числа в алгебраической форме
     * @param imaginary - мнимая часть мнимого числа в алгебраической форме
     */
    public ComplexNumberAlgebraic(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Сложение двух комплексных чисел
     * @param val - второе слагаемое
     * @return сумма двух комплексных чисел
     */
    @Override
    public ComplexNumber add(ComplexNumber val) {
        return new ComplexNumberAlgebraic(this.real + val.getReal(), this.imaginary + val.getImaginary());
    }

    /**
     * Вычитание одного комплексного числа из другого
     * @param val - вычитаемое
     * @return разность двух комплексных чисел
     */
    @Override
    public ComplexNumber subtract(ComplexNumber val) {
        return new ComplexNumberAlgebraic(this.real - val.getReal(), this.imaginary - val.getImaginary());
    }

    /**
     * Умножение комплексного числа на аргумент
     * @param val - второе сомножитель
     * @return произведение комплексного числа и аргумента метода
     */
    @Override
    public ComplexNumber multiply(ComplexNumber val) {
        double resultReal = this.real * val.getReal() - this.imaginary * val.getImaginary();
        double resultImaginary = this.imaginary * val.getReal() + this.real * val.getImaginary();

        return new ComplexNumberAlgebraic(resultReal, resultImaginary);
    }

    /**
     * Деление комплексного числа на аргумент
     * @param val - делитель
     * @return частное комплексного числа и аргумента метода
     */
    @Override
    public ComplexNumber divide(ComplexNumber val) {
        double denominator = val.getReal() * val.getReal() + val.getImaginary() * val.getImaginary();
        double resultReal = (this.real * val.getReal() + this.imaginary * val.getImaginary()) / denominator;
        double resultImaginary = (this.imaginary * val.getReal() - this.real * val.getImaginary()) / denominator;

        return new ComplexNumberAlgebraic(resultReal, resultImaginary);
    }

    /**
     * Возвращает модуль комплексного числа
     * @return модули комплексного числа
     */
    public double getAbs() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    @Override
    public double getReal() {
        return this.real;
    }

    @Override
    public double getImaginary() {
        return this.imaginary;
    }

    /**
     * Возвращает представление комплексного числа в виде строки вида Re + j * Im, где
     * Re - действительная часть, Im - мнимая часть, j - мнимая единица
     * @return Строковое представление комплексного числа
     */
    @Override
    public String toString() {
        if (this.imaginary > 0) {
            return this.real + " + j" + this.imaginary;
        } else if (this.imaginary < 0) {
            return this.real + " - j" + Math.abs(this.imaginary);
        } else {
            return this.real + " +j0.0";
        }
    }

}