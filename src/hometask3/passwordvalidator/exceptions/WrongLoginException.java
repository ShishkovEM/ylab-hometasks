package hometask3.passwordvalidator.exceptions;

public class WrongLoginException extends Exception {

    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }

    public WrongLoginException(Throwable cause) {
        super(cause);
    }

    public WrongLoginException(String message, Throwable cause) {
        super(message, cause);
    }

}

