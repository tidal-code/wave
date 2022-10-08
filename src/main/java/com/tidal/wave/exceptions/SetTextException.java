package com.tidal.wave.exceptions;

/**
 * Thrown to indicate that although an exception happened while trying to set text with setText() method
 */
public class SetTextException extends RuntimeException {
    public SetTextException(String message) {
        super(message);
    }

    public SetTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
