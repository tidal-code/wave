package com.tidal.wave.exceptions;

public class AzureOperationsException extends RuntimeException {

    private static final long serialVersionUID = 756532423246L;

    public AzureOperationsException(String message) {
        super(message);
    }
}
