package hometask2.complexnumbers;

import java.util.Random;

public class ComplexNumberTest {
    static double BOUND = 10.0;
    static Random rnd = new Random(System.currentTimeMillis());
    static ComplexNumber complexNumber1 = new ComplexNumberAlgebraic(rnd.nextDouble(BOUND), rnd.nextDouble(BOUND));
    static ComplexNumber complexNumber2 = new ComplexNumberAlgebraic(rnd.nextDouble(BOUND), rnd.nextDouble(BOUND));

    public static void main(String[] args) {
        System.out.println("Первое число: " + complexNumber1);
        System.out.println("Второе число: " + complexNumber2);
        System.out.println("------------");
        System.out.println("Сумма первого и второго числа:");
        System.out.println(complexNumber1.add(complexNumber2));
        System.out.println("------------");
        System.out.println("Разность первого и второго числа:");
        System.out.println(complexNumber1.subtract(complexNumber2));
        System.out.println("------------");
        System.out.println("Произведение первого и второго числа:");
        System.out.println(complexNumber1.multiply(complexNumber2));
        System.out.println("------------");
        System.out.println("Частное первого и второго числа:");
        System.out.println(complexNumber1.divide(complexNumber2));
        System.out.println("------------");
        System.out.println("Модуль первого числа:");
        System.out.println(complexNumber1.getAbs());
        System.out.println("------------");
        System.out.println("Модуль второго числа:");
        System.out.println(complexNumber2.getAbs());
        System.out.println("------------");
    }

}