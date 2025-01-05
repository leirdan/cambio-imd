package br.ufrn.imd.cambio_imd.models.cards;

import br.ufrn.imd.cambio_imd.models.IDrawable;

import java.util.Collections;
import java.util.Optional;

/**
 * Classe que representa a pilha de reposição de cartas
 */
public class DrawPile extends Deck implements IDrawable {
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

    /// @implSpec
    @Override
    public Card drawCard(int cardIndex) {
        return cards.remove(cardIndex);
    }
}
