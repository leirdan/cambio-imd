package br.ufrn.imd.cambio_imd.exceptions;

public class UnitializedGameException extends RuntimeException {
    public UnitializedGameException() {
        super("Game was not initialized!");
    }
    public UnitializedGameException(String message) {
        super(message);
    }
}
