package br.ufrn.imd.cambio_imd.controllers.mediators;

import br.ufrn.imd.cambio_imd.commands.GeneratePlayersOrderCommand;
import br.ufrn.imd.cambio_imd.commands.ICommand;
import br.ufrn.imd.cambio_imd.commands.PlayerDiscardCardOnPileCommand;
import br.ufrn.imd.cambio_imd.commands.PlayerDrawCardFromPileCommand;

public class GameMediator implements IGameMediator {
    // TODO: uma alternativa à ficar criando novos métodos toda vez em GameMediator é
    // usar esse método genérico que aceita qualquer tipo de command e o executa,
    // respeitando ainda mais o Open/Closed Principle.
    // Exemplo de uso:
    // var command = new GeneratePlayersOrderCommand(...);
    // mediator.executeAction(command); e tchan
    @Override
    public void executeAction(ICommand command) {
        command.execute();
    }

    @Override
    public void generateOrder(GeneratePlayersOrderCommand command) {
       command.execute();
    }

    @Override
    public void playerDrawCard(PlayerDrawCardFromPileCommand command) {
        command.execute();
    }

    @Override
    public void playerDiscardCard(PlayerDiscardCardOnPileCommand command) {
        command.execute();
    }

}
