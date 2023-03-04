package hometask1.pell;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

/**
 * На вход подается число n (0 <= n <= 30), необходимо распечатать n-e число Пелля
 */
public class Pell {

    // Создадим статический кэш для мемоизации вычислений
    static HashMap<Integer, BigInteger> cash = new HashMap<>();

    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                if (n < 0) {
                    throw new RuntimeException("Введите положительное число!");
                }
                System.out.println(pell(n));
            } else {
                throw new RuntimeException("Некорректный ввод!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Функция вычисляет и возвращает n-й элемент последовательности Пелля
     *
     * @param n - номер элемента из последовательности Пелля
     * @return n-й элемент последовательности Пелля
     */
    static BigInteger pell(int n) {
        cash.put(0, BigInteger.ZERO);
        cash.put(1, BigInteger.ONE);
        if (!cash.containsKey(n)) {
            cash.put(n, BigInteger.TWO.multiply(pell(n - 1)).add(pell(n - 2)));
        }
        return cash.get(n);
    }
}