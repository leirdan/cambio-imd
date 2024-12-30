package br.ufrn.imd.cambio_imd.models;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.players.CardHand;

/**
 * Representa o comportamento de puxar uma carta, ou seja,
 * retirar um elemento da lista e retorná-lo.
 * <p>
 * Deve ser implementada pelas classes <code>CardHand</code> e <code>DrawPile</code>.
 * </p>
 */
public interface IDrawable {
    /**
     * Ação de "puxar uma carta" de si e retorná-la, eliminando da lista de cartas.
     *
     * @param cardIndex Índice da carta na lista.
     * @return Carta selecionada.
     */
    Card drawCard(int cardIndex);
}
