package com.tidal.wave.exceptions;

public class ExpectationFailure extends RuntimeException {
    public ExpectationFailure(String message) {
        super(message);
    }

    public ExpectationFailure(Throwable cause) {
        super(cause);
    }

    public ExpectationFailure(String message, Throwable cause) {
        super(message, cause);
    }
}
