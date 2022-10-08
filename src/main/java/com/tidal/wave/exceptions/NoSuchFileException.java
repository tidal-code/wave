package com.tidal.wave.exceptions;

public class NoSuchFileException extends RuntimeException {
    public NoSuchFileException(String message) {
        super(message);
    }
}
