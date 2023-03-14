package hometask2.sequences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Класс предназначен для демонстрации работы методов класса SequenceImpl.
 * В целях генерализации кода методы вызываются через рефлексию.
 */
public class SequenceTest {
    static int N_BOUND = 10; // Верхняя граница числа для рандомайзера
    static Random rnd = new Random(System.currentTimeMillis()); // Рандомайзер для аргумента проверяемых методов
    static int n = rnd.nextInt(N_BOUND); // Проверочное число элементов последовательности от 0 до 9.
                                         // 0 оставляем намеренно для проверки соответствующего случая

    public static void main(String[] args) {
        // Создадим новый объект SequenceImpl, реализующий интерфейс SequenceGenerator
        SequenceGenerator sequenceGenerator = new SequenceImpl();

        // Сформируем массив из методов объекта sequenceGenerator
        Method[] methods = sequenceGenerator.getClass().getDeclaredMethods();

        // Отсортируем методы в массиве по имени и для каждого вызовем testSequenceMethod
        Arrays.stream(methods)
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> testSequenceMethod(sequenceGenerator, method));
    }

    /**
     * Метод рандомизирует значение n
     */
    static void randomizeN() {
        rnd.setSeed(System.nanoTime());
        n = rnd.nextInt(N_BOUND);
    }

    /**
     * Метод вызываевает один тестируемый метод из класса SequenceImpl
     */
    static void testSequenceMethod(SequenceGenerator testedObject, Method testedMethod) {
        System.out.println("Проверяем последовательность " + testedMethod.getName().toUpperCase() + " для N = " + n + ":");
        try {
            testedMethod.invoke(testedObject, n);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----------");
        randomizeN();
    }
}
