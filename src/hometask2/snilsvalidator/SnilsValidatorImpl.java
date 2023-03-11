package hometask2.snilsvalidator;

/**
 * Номер СНИЛС состоит из 11 цифра, и валидация номера СНИЛС выполняется по следующим правилам:
 * 1. Вычислить сумму произведений цифр СНИЛС (с 1-й по 9-ю) на следующие коэффициенты — 9, 8, 7, 6, 5, 4, 3, 2, 1 (т.е. номера цифр в обратном порядке).
 * 2. Вычислить контрольное число от полученной суммы следующим образом:
 * 2.1. если она меньше 100, то контрольное число равно этой сумме;
 * 2.2. если равна 100, то контрольное число равно 0;
 * 2.3. если больше 100, то вычислить остаток от деления на 101 и далее:
 * 2.3.1. если остаток от деления равен 100, то контольное число равно 0;
 * 2.3.2. в противном случае контрольное число равно вычисленному остатку от деления.
 * 3. Сравнить полученное контрольное число с двумя младшими разрядами СНИЛС. Если они равны, то СНИЛС верный.
 */
public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {

        /*
        Проверяем, что введённый СНИЛС соответствует одному из паттернов:
        XXXXXXXXXXX — маска ввода без разделителей.
        XXX-XXX-XXX-XX — маска ввода с разделителями.
        XXX-XXX-XXX XX — маска ввода с разделителями и с отделением контрольного числа.
         */
        boolean isLegalSequence = snils.matches("^\\d{3}-\\d{3}-\\d{3} \\d{2}$") ||
                                  snils.matches("^\\d{3}-\\d{3}-\\d{3}-\\d{2}$") ||
                                  snils.matches("^\\d{11}$");
        if (!isLegalSequence) {
            return false;
        }

        // Удалим все нецифровые символы
        snils = snils.replaceAll("\\D", "");

        // Сумма произведений цифр СНИЛС (с 1-й по 9-ю) на номера цифр в обратном порядке
        int controlSum = 0;
        for (int i = 0; i < 9; i++) {
            controlSum += Integer.parseInt(String.valueOf(snils.charAt(i))) * (9 - i);
        }

        // Число из 10-й и 11-й цифр СНИЛС
        int lastTwoDigits = Integer.parseInt(String.valueOf(snils.charAt(9)).concat(String.valueOf(snils.charAt(10))));

        // Делаем проверки по п.2 задания
        if (controlSum < 100 && controlSum != lastTwoDigits) {
            return false;
        } else if (controlSum == 100 && lastTwoDigits != 0) {
            return false;
        } else {
            if (controlSum % 101 == 100 && lastTwoDigits != 0) {
                return false;
            } else if (controlSum % 101 != 100 && lastTwoDigits != controlSum % 101) {
                return false;
            }
        }

        // В случае, если предыдущие проверки пройдены, из метода возвращается true
        return true;
    }

}
