package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.enums.PlayerType;
import br.ufrn.imd.cambio_imd.exceptions.EmptyCardException;
import br.ufrn.imd.cambio_imd.models.Entity;
import br.ufrn.imd.cambio_imd.models.cards.Card;

public class Player extends Entity {
    /*
     * Informações do jogador
     */
    private String name = "";
    private CardHand cardHand = new CardHand();
    private PlayerType type = PlayerType.ROBOT;

    /*
     * Informações de ação
     */

    //< De corte
    private boolean wrongCut = false;
    private boolean prohibitedCut = false;

    public Player() {
        super();
    }

    public Player(String name, PlayerType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public Player(String name, boolean wrongCut, CardHand cardHand, PlayerType type) {
        super();
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
        this.type = type;
    }

    public Player(int id, String name, boolean wrongCut, CardHand cardHand, PlayerType type) {
        super(id);
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
        this.type = type;
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

    public CardHand getHand() {
        return cardHand;
    }

    public void setHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }

    public void addCard(Card card) {
        cardHand.addCard(card);
    }

    public Card removeCard(int index) {
        return cardHand.removeCard(index);
    }

    public boolean isProhibitedCut() {
        return prohibitedCut;
    }

    public void setProhibitedCut(boolean prohibitedCut) {
        this.prohibitedCut = prohibitedCut;
    }

    public boolean isWrongCut() {
        return wrongCut;
    }

    public void setWrongCut(boolean wrongCut) {
        this.wrongCut = wrongCut;
    }

    /**
     * Informa se o jogador é humano ou robô.
     * @return
     */
    public boolean isHuman(){
        return this.type == PlayerType.HUMAN;
    }
}

