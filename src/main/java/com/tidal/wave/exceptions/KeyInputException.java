package com.tidal.wave.exceptions;

/**
 * Thrown to indicate that although an exception happened while trying to set text with setText() method
 */
public class KeyInputException extends RuntimeException {
    private static final long serialVersionUID = 87653456L;

    public KeyInputException(String message) {
        super(message);
    }

    public KeyInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
