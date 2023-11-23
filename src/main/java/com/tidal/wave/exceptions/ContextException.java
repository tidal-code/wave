package com.tidal.wave.exceptions;

public class ContextException extends RuntimeException {

    private static final long serialVersionUID = 987651345423L;

    public ContextException(String message) {
        super(message);
    }

    public ContextException(Throwable cause) {
        super(cause);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
