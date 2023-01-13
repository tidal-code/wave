package com.tidal.wave.exceptions;

public class IterationStopper extends RuntimeException {
    public IterationStopper(String message) {
        super(message);
    }

    public IterationStopper(Throwable cause) {
        super(cause);
    }

    public IterationStopper(String message, Throwable cause) {
        super(message, cause);
    }
}
