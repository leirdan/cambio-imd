package br.ufrn.imd.cambio_imd.models;

/**
 * Classe abstrata que representa uma entidade genérica.
 * Serve apenas para identificação por id.
 */
public abstract class Entity {
    private static int SERIAL_ID = 1;
    protected int id;

    /**
     * Construtor padrão da classe.
     * Atribui um id único para cada instância.
     */
    public Entity() {
        this.id = SERIAL_ID;
        SERIAL_ID++;
    }

    /**
     * Construtor da classe.
     * Atribui um id único para cada instância.
     * @param id id da instância.
     */
    public Entity(int id) {
        this.id = id;
    }

    /**
     * Método que retorna o id da instância.
     * @return id da instância.
     */
    public int getId() {
        return id;
    }

    /**
     * Método que atribui um id para a instância.
     * @param id id da instância.
     */
    public void setId(int id) {
        this.id = id;
    }

}
