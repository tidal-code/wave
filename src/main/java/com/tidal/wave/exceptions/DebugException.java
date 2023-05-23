package com.tidal.wave.exceptions;

public class DebugException extends RuntimeException {
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
