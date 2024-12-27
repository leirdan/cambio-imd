package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.models.Entity;

public class Player extends Entity {
    private String name = "";
    private boolean wrongCut = false;
    private Hand hand = new Hand();

    public Player() {
        super();
    }

    public Player(String name, boolean wrongCut, Hand hand) {
        super();
        this.name = name;
        this.wrongCut = wrongCut;
        this.hand = hand;
    }

    public Player(int id, String name, boolean wrongCut, Hand hand) {
        super(id);
        this.name = name;
        this.wrongCut = wrongCut;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean madeWrongCut() {
        return wrongCut;
    }

    public void setWrongCut(boolean wrongCut) {
        this.wrongCut = wrongCut;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
