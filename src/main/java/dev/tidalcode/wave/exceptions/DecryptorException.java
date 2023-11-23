package dev.tidalcode.wave.exceptions;

public class DecryptorException extends RuntimeException {

    private static final long serialVersionUID = 3456524435245L;

    public DecryptorException(String message) {
        super(message);
    }

    public DecryptorException(Throwable cause) {
        super(cause);
    }

    public DecryptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
