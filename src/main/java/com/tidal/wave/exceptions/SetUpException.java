package com.tidal.wave.exceptions;

public class SetUpException extends RuntimeException {
    private static final long serialVersionUID = 87652345342L;

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
