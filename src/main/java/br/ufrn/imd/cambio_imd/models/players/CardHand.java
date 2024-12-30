package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.models.IDrawable;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.Deck;

/**
 * Classe que representa a mão de cartas do jogador.
 */
public class CardHand extends Deck implements IDrawable {
    /**
     * @return
     */
    public int computePoints() {
        int points = 0;
        for (var card : cards) {
            points += card.getValue();
        }
        return points;
    }

    /**
     * Recupera a quantidade de cartas na mão
     */
    public int getAmount() {
        return cards.size();
    }

    /// @implSpec
    @Override
    public Card drawCard(int cardIndex) {
        return cards.remove(cardIndex);
    }
}
