package br.ufrn.imd.cambio_imd.exceptions;

public class EmptyCardException extends RuntimeException {
    public EmptyCardException() {
        super("Empty card is not valid in game!");
    }

    public EmptyCardException(String message) {
        super(message);
    }
}
