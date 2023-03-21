package hometask3.passwordvalidator;

import hometask3.passwordvalidator.exceptions.WrongLoginException;
import hometask3.passwordvalidator.exceptions.WrongPasswordException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. Создать статический метод, который принимает на вход три параметра: login,
 * password и confirmPassword.
 * 2. Login должен содержать только латинские буквы, цифры и знак подчеркивания.
 * Если login не соответствует - выбросить WrongLoginException с текстом “Логин
 * содержит недопустимые символы”
 * 3. Длина login должна быть меньше 20 символов. Если login не соответствует этим
 * требованиям, необходимо выбросить WrongLoginException с текстом “Логин
 * слишком длинный”
 * 4. Password должен содержать только латинские буквы, цифры и знак
 * подчеркивания. Если password не соответствует этим требованиям, необходимо
 * выбросить WrongPasswordException с текстом “Пароль содержит недопустимые
 * символы”
 * 5. Длина password должна быть меньше 20 символов. Если password не
 * соответствует этим требованиям, необходимо выбросить
 * WrongPasswordException с текстом “Пароль слишком длинный”
 * 6. Также password и confirmPassword должны быть равны. Если password не
 * соответствует этим требованиям, необходимо выбросить
 * WrongPasswordException с текстом “Пароль и подтверждение не совпадают”
 * 7. WrongPasswordException и WrongLoginException - пользовательские классы
 * исключения с двумя конструкторами – один по умолчанию, второй принимает
 * сообщение исключения и передает его в конструктор класса Exception.
 * 8. Обработка исключений проводится внутри метода. Обработка исключений -
 * вывод сообщения об ошибке консоль
 * 9. Метод возвращает true, если значения верны или false в другом случае.
 */
public class PasswordValidator {
    static String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_";
    static Set<Character> ALLOWED_CHARS = allowedChars.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toSet());

    private PasswordValidator() {
    }

    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            if (checkForUnallowedChars(login)) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }

            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }

            if (checkForUnallowedChars(password)) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }

            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }

            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }

        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Метод проверяет строку на содержание в ней недопустимых символов
     *
     * @param inputString - проверяемая строка
     * @return true - если в переданной строке есть недопустимые символы, false - если нет
     */
    public static boolean checkForUnallowedChars(String inputString) {
        Set<Character> inputStringCharSet = inputString.toLowerCase()
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        inputStringCharSet.removeAll(ALLOWED_CHARS);
        return !inputStringCharSet.isEmpty();
    }

}
