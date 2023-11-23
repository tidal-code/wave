package dev.tidalcode.wave.exceptions;

public class PendingException extends RuntimeException {
    private static final long serialVersionUID = 4423343423L;

    public PendingException(String message) {
        super(message);
    }
}
