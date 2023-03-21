package hometask3.passwordvalidator;

public class PasswordValidatorTest {
    static String validLogin = "AmazingLogin1_";
    static String unallowedCharsLogin = "*923-";
    static String tooLongLogin = "12345678901234567890";
    static String validPass = "AmazingPass1_";
    static String unallowedCharsPass = "*785-";
    static String tooLongPass = "12345678901234567890";
    static String yetAnotherValidPass = "AmazingPass2_";

    public static void main(String[] args) {
        System.out.println(PasswordValidator.validate(validLogin, validPass, validPass)); // true
        System.out.println("---");
        System.out.println(PasswordValidator.validate(unallowedCharsLogin, validPass, validPass)); // Логин содержит недопустимые символы /n false
        System.out.println("---");
        System.out.println(PasswordValidator.validate(tooLongLogin, validPass, validPass)); // Логин слишком длинный /n false
        System.out.println("---");
        System.out.println(PasswordValidator.validate(validLogin, unallowedCharsPass, unallowedCharsPass)); // Пароль содержит недопустимые символы /n false
        System.out.println("---");
        System.out.println(PasswordValidator.validate(validLogin, tooLongPass, tooLongPass)); // Пароль слишком длинный /n false
        System.out.println("---");
        System.out.println(PasswordValidator.validate(validLogin, yetAnotherValidPass, validPass)); // Пароль и подтверждение не совпадают /n false
        System.out.println("---");
    }

}
