package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.models.cards.Deck;

/**
 * Classe que representa a mão de cartas do jogador.
 */
public class CardHand extends Deck {
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

}
