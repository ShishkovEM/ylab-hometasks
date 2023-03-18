package hometask3.passwordvalidator.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }

    public WrongPasswordException(Throwable cause) {
        super(cause);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
