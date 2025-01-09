package br.ufrn.imd.cambio_imd.models.cards;

import java.util.Collections;
import java.util.Optional;

/**
 * Classe que representa a pilha de reposição de cartas
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

    public Card removeTopCard() {
        if (!this.isEmpty())
            return cards.pop();

        return null;
    }
}
