package br.ufrn.imd.cambio_imd.models.cards;

import java.util.Stack;

public abstract class Deck {
    protected Stack<Card> cards;

    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void addCard(Card card) {
        if (card == null) return;

        cards.push(card);
    }

}
