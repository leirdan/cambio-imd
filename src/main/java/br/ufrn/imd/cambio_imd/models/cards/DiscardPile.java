package br.ufrn.imd.cambio_imd.models.cards;

import br.ufrn.imd.cambio_imd.exceptions.EmptyDeckException;

import java.util.EmptyStackException;

/**
 * Classe que representa a pilha de cartas descartadas.
 * É onde os jogadores descartam suas cartas, seja na rodada de corte
 * ou na rodada normal.
 * Herda de @see Deck.
 * Armazena objetos do tipo @see Card.
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
        } catch (EmptyDeckException ex) {
            System.out.println("Pilha de descarte está vazia!");
            return false;
        }
    }
    
    /**
     * Retorna a carta que está no topo da pilha central.
     * @return Carta que está no topo da pilha central.
     */
    public Card getCardOnTop() {
        return cards.peek();
    }
}

