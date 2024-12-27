package br.ufrn.imd.cambio_imd.models;

public abstract class Entity {
    // TODO: no momento compartilha os ids entre players e cards
    // Ou seja, se o player tem Id 1 e vc cria um novo Card, o card terá id 2
    // Ver se há a necessidade de mudar isso.

    private static int SERIAL_ID = 1;
    protected int id;

    public Entity() {
        this.id = SERIAL_ID;
        SERIAL_ID++;
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
