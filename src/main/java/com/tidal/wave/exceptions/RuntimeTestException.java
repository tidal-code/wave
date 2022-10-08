package com.tidal.wave.exceptions;

public class RuntimeTestException extends RuntimeException {
    public RuntimeTestException(String message) {
        super(message);
    }

    public RuntimeTestException(Throwable cause) {
        super(cause);
    }

    public RuntimeTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
