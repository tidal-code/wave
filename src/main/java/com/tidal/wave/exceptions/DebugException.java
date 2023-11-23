package com.tidal.wave.exceptions;

public class DebugException extends RuntimeException {
    private static final long serialVersionUID = 98765334563L;

    public DebugException(String message) {
        super(message);
    }

    public DebugException(Throwable cause) {
        super(cause);
    }

    public DebugException(String message, Throwable cause) {
        super(message, cause);
    }
}
