package br.ufrn.imd.cambio_imd.exceptions;

public class UnitializedGameException extends RuntimeException {
    public UnitializedGameException() {
        super("O jogo não foi inicializado corretamente!");
    }
    public UnitializedGameException(String message) {
        super(message);
    }
}
