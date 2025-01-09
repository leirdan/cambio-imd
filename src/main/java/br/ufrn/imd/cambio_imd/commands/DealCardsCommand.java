package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.utility.DeckGenerator;

public class DealCardsCommand implements ICommand {
    @Override
    public void execute() {
        GameContext context = GameContext.getInstance();
        context.getDrawPile().setCards(DeckGenerator.generate());
        context.getDrawPile().shuffle();
    }
}
