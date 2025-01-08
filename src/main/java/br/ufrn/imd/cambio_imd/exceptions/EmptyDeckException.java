package br.ufrn.imd.cambio_imd.exceptions;

public class EmptyDeckException extends RuntimeException {
    public EmptyDeckException() {
        super("Card's deck is empty!");
    }

    public EmptyDeckException(String message) {
        super(message);
    }

    public EmptyDeckException(String message, Throwable cause) {
        super(message, cause);
    }
}
