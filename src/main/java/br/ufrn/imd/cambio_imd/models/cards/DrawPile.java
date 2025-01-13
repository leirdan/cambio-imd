package br.ufrn.imd.cambio_imd.models.cards;

import java.util.Collections;
import java.util.Optional;

/**
 * Classe que representa a pilha de reposição de cartas.
 * Basicamente, é de onde os jogadores compram as cartas de reposição.
 * Herda de @see Deck.
 * Armazena objetos do tipo @see Card.
 */
public class DrawPile extends Deck {
    public DrawPile() {
        super();
    }

    /**
     * Embaralha a pilha de reposição.
     * Usado comumente após adição ou remoção de cartas.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Retira uma carta do topo da pilha de reposição.
     * @return Carta a ser removida do topo.
     */
    public Card removeTopCard() {
        if (!this.isEmpty())
            return cards.pop();

        return null;
    }
}
