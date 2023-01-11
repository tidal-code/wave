package com.tidal.wave.exceptions;

public class TestAssertionError extends RuntimeException {
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
