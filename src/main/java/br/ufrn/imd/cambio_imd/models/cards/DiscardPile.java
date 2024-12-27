package br.ufrn.imd.cambio_imd.models.cards;

/**
 * Classe que representa a pilha de descartes do jogo
 */
public class DiscardPile extends Deck {
    /**
     * Compara uma carta específica com a carta do topo da pilha
     * @param otherCard Carta específica
     * @return Booleano
     */
    public boolean isCardOnTopEqualsTo(Card otherCard) {
        var card = cards.peek();
        return card.equals(otherCard);
    }
}
