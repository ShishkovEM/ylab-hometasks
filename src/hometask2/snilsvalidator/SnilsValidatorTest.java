package hometask2.snilsvalidator;

public class SnilsValidatorTest {
    static String validSnils1 = "53184891104";
    static String validSnils2 = "447-434-469-03";
    static String validSnils3 = "237-549-027 87";
    static String invalidSnils1 = "88724516352";
    static String invalidSnils2 = "171-468-184-37";
    static String invalidSnils3 = "682-593-064 73";
    static String invalidSnils4 = "1 2 3 4 5";

    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();
        System.out.println("Валидация СНИЛС " + validSnils1 + ":");
        System.out.println(snilsValidator.validate(validSnils1));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + validSnils2 + ":");
        System.out.println(snilsValidator.validate(validSnils2));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + validSnils3 + ":");
        System.out.println(snilsValidator.validate(validSnils3));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + invalidSnils1 + ":");
        System.out.println(snilsValidator.validate(invalidSnils1));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + invalidSnils2 + ":");
        System.out.println(snilsValidator.validate(invalidSnils2));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + invalidSnils3 + ":");
        System.out.println(snilsValidator.validate(invalidSnils3));
        System.out.println("----------");
        System.out.println("Валидация СНИЛС " + invalidSnils4 + ":");
        System.out.println(snilsValidator.validate(invalidSnils4));
        System.out.println("----------");
    }
}
