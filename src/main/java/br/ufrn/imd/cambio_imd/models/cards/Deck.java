package br.ufrn.imd.cambio_imd.models.cards;

import br.ufrn.imd.cambio_imd.exceptions.EmptyCardException;
import br.ufrn.imd.cambio_imd.exceptions.EmptyDeckException;

import java.util.Stack;

public abstract class Deck {
    protected Stack<Card> cards;

    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    /**
     * Recupera a quantidade de cartas na mão
     */
    public int getAmount() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void addCard(Card card) throws EmptyCardException {
        if (card == null) throw new EmptyCardException();

        cards.push(card);
    }

    public Card removeCard(int cardIndex) throws
            ArrayIndexOutOfBoundsException, EmptyDeckException {
        if (this.isEmpty())
            throw new EmptyDeckException();

        else if (cardIndex >= cards.size() || cardIndex < 0)
            throw new ArrayIndexOutOfBoundsException("Invalid index!");

        return cards.remove(cardIndex);
    }

}
