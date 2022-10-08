package com.tidal.wave.exceptions;

public class SetUpException extends RuntimeException {
    public SetUpException(String message) {
        super(message);
    }

    public SetUpException(Throwable cause) {
        super(cause);
    }

    public SetUpException(String message, Throwable cause) {
        super(message, cause);
    }
}
