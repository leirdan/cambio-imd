package br.ufrn.imd.cambio_imd.models.cards;

import java.util.EmptyStackException;

/**
 * Classe que representa a pilha de cartas descartadas
 */
public class DiscardPile extends Deck {
    /**
     * Compara uma carta específica com a carta do topo da pilha
     *
     * @param otherCard Carta específica
     * @return Booleano
     */
    public boolean isCardOnTopEqualsTo(Card otherCard) {
        try {
            var card = cards.peek();
            return card.equals(otherCard);
        } catch (EmptyStackException ex) {
            System.out.println("Pilha de descarte está vazia!");
            return false;
        }
    }

    public Card getCardOnTop() {
        return cards.peek();
    }
}
