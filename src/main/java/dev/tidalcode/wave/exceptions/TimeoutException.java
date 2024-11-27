package dev.tidalcode.wave.exceptions;

public class TimeoutException extends RuntimeException {
    private static final long serialVersionUID = 3544132423L;

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
