package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.models.cards.Deck;

/**
 * Classe que representa a mão de cartas do jogador.
 * Herda de @see Deck.
 * Armazena objetos do tipo @see Card.
 */
public class CardHand extends Deck {

    /**
     * Essa função é primordial na lógica de câmbio.
     * É necessário saber a soma das cartas para que se saiba qual o jogador
     * que tem a menor pontuação.
     * @return Retorna a somatória dos valores das cartas da mão.
     */
    public int computePoints() {
        int points = 0;
        for (var card : cards) {
            points += card.getValue();
        }
        return points;
    }
}
