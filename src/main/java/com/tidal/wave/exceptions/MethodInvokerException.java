package com.tidal.wave.exceptions;

public class MethodInvokerException extends RuntimeException {
    public MethodInvokerException(String message) {
        super(message);
    }

    public MethodInvokerException(Throwable cause) {
        super(cause);
    }

    public MethodInvokerException(String message, Throwable cause) {
        super(message, cause);
    }
}
