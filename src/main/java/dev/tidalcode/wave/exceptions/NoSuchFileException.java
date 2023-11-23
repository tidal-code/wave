package dev.tidalcode.wave.exceptions;

public class NoSuchFileException extends RuntimeException {
    private static final long serialVersionUID = 234553643225L;

    public NoSuchFileException(String message) {
        super(message);
    }
}
