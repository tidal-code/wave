package com.tidal.wave.exceptions;

public class XMLHandlerException extends RuntimeException {
    private static final long serialVersionUID = 564445412L;

    public XMLHandlerException() {
    }

    public XMLHandlerException(String message) {
        super(message);
    }

    public XMLHandlerException(Throwable cause) {
        super(cause);
    }

    public XMLHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
