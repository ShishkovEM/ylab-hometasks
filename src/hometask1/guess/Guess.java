package hometask1.guess;

import java.util.Random;
import java.util.Scanner;

/**
 * Игра угадайка. При запуске программа загадывает число от 1 до 99 (включительно) и дает пользователю 10 попыток отгадать.
 * Далее пользователь начинает вводить число. И тут возможен один из следующих вариантов:
 * - Пользователь отгадал число. В таком случае выводится строка
 * “Ты угадал с N попытки”, где N - номер текущей попытки пользователя
 * - Пользователь ввел число, меньше загаданного.
 * В таком случае выводится сообщение “Мое число меньше! У тебя осталось M попыток” где M - количество оставшихся попыток
 * - Пользователь ввел число, больше загаданного.
 * В таком случае выводится сообщение “Мое число больше! У тебя осталось M попыток” где M - количество оставшихся попыток
 * - У пользователя закончились попытки и число не было угадано. В таком случае выводится сообщение “Ты ну угадал”
 */
public class Guess {
    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(100); // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        int currentAttemptNumber = 1; // номер текущей попытки
        int currentGuess; // текущая догадка
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            while (currentAttemptNumber <= maxAttempts) {
                if (scanner.hasNextInt()) {
                    currentGuess = scanner.nextInt();
                    if (currentGuess == number) {
                        System.out.println("Ты угадал с " + currentAttemptNumber + " попытки!");
                        return;
                    } else if (currentGuess < number) {
                        System.out.println("Мое число больше! Осталось " + (maxAttempts - currentAttemptNumber) + " попыток!");
                        currentAttemptNumber++;
                    } else {
                        System.out.println("Мое число меньше! Осталось " + (maxAttempts - currentAttemptNumber) + " попыток!");
                        currentAttemptNumber++;
                    }
                } else {
                    System.out.println("Ошибка! Введено нечисловое значение! Игра завершена!");
                    return;
                }
            }
            System.out.println("Ты не угадал!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
