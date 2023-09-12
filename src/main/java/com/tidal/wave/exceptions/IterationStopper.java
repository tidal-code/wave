package com.tidal.wave.exceptions;

public class IterationStopper extends Exception {
    private static final long serialVersionUID = 123452423423426789L;

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
