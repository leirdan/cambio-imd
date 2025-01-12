package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.utility.RandomGenerator;

public class PutFirstCardOnDiscardPileCommand implements ICommand {

    private DiscardPile discardPile;
    private DrawPile drawPile;

    public PutFirstCardOnDiscardPileCommand(DiscardPile discardPile, DrawPile drawPile) {
        this.discardPile = discardPile;
        this.drawPile = drawPile;
    }

    @Override
    public void execute() {
        int randomIndex = RandomGenerator.getInt(drawPile.getAmount());

        Card randomCard = drawPile.getCards().remove(randomIndex);

        discardPile.addCard(randomCard);
    }
}
