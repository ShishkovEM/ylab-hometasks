package hometask1.stars;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Программе передается 3 параметра: количество строк, количество столбцов, произвольный симов.
 * Необходимо вывести фигуру, состоящую из заданного списка строк и заданного количества столбцов,
 * и каждый элемент в которой равен указанному символу.
 */
public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt(); // Число строк в двумерном массиве

                if (scanner.hasNextInt()) {
                    int m = scanner.nextInt(); // Число столбцов в двумерном массиве
                    String template = scanner.next(); // Элемент, которым заполняется массив
                    if (n < 1 || m < 1) {
                        throw new RuntimeException("Размерность массива не может быть меньше единицы!");
                    }

                    // Инициализируем двумерный массив размерностью n x m
                    String[][] result = new String[n][m];

                    // Заполняем массив
                    Arrays.stream(result)                                   // Создаём стрим из строк
                            .forEach(arr -> Arrays.fill(arr, template));    // Каждый элемент в строке заполняем

                    // Выводим заполненный массив в консоль
                    for (int i = 0; i < result.length; i++) {
                        for (int j = 0; j < result[i].length; j++) {
                            if (i < result.length - 1) {
                                System.out.print(j == result[i].length - 1 ? result[i][j] + System.lineSeparator() : result[i][j] + " ");
                            } else {
                                System.out.print(j == result[i].length - 1 ? result[i][j] : result[i][j] + " ");
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("Некорректно введено число cтолбцов!");
                }
            } else {
                throw new RuntimeException("Некорректно введено число строк!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
