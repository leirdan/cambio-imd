package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.utility.DeckGenerator;

public class DealCardsCommand implements ICommand {
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();
        context.getDrawPile().setCards(DeckGenerator.generate());
        context.getDrawPile().shuffle();
    }
}
