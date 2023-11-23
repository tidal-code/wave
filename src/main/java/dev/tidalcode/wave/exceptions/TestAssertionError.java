package dev.tidalcode.wave.exceptions;

public class TestAssertionError extends RuntimeException {

    private static final long serialVersionUID = 4563345353534L;

    public TestAssertionError(String message) {
        super(message);
    }

    public TestAssertionError(Throwable cause) {
        super(cause);
    }

    public TestAssertionError(String message, Throwable cause) {
        super(message, cause);
    }
}
