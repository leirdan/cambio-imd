package br.ufrn.imd.cambio_imd.models;

import br.ufrn.imd.cambio_imd.models.cards.Card;

/**
 * Interface
 */
public interface IDrawable {
    Card drawCard(int cardIndex);
}
