package br.ufrn.imd.cambio_imd.controllers.mediators;

import br.ufrn.imd.cambio_imd.commands.ExchangeCardCommand;

public class GameMediator implements IGameMediator {

    @Override
    public void exchangeCardsBetweenPlayerAndDeck(ExchangeCardCommand command) {
        command.execute();
    }
}
