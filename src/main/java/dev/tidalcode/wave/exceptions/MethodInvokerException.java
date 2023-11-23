package dev.tidalcode.wave.exceptions;

public class MethodInvokerException extends RuntimeException {
    private static final long serialVersionUID = 567765423423L;

    public MethodInvokerException(String message) {
        super(message);
    }

    public MethodInvokerException(Throwable cause) {
        super(cause);
    }

    public MethodInvokerException(String message, Throwable cause) {
        super(message, cause);
    }
}
