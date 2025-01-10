package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.exceptions.EmptyCardException;
import br.ufrn.imd.cambio_imd.models.Entity;
import br.ufrn.imd.cambio_imd.models.cards.Card;

public class Player extends Entity {
    private String name = "";
    private boolean wrongCut = false;
    private boolean prohibitedCut = false;
    private CardHand cardHand = new CardHand();

    private int cardIndex = -1;

    public Player() {
        super();
    }

    public Player(String name) {
        super();
        this.name = name;
    }

    public Player(String name, boolean wrongCut, CardHand cardHand) {
        super();
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
    }

    public Player(int id, String name, boolean wrongCut, CardHand cardHand) {
        super(id);
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
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

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public boolean isWrongCut() {
        return wrongCut;
    }

    public void setWrongCut(boolean wrongCut) {
        this.wrongCut = wrongCut;
    }
    
}
