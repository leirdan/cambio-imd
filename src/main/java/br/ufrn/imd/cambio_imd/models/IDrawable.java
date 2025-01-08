package br.ufrn.imd.cambio_imd.models;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.players.CardHand;

// TODO: talvez remover essa
public interface IDrawable {
    Card drawCard(int cardIndex);
}
