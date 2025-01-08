package br.ufrn.imd.cambio_imd.controllers.mediators;

import br.ufrn.imd.cambio_imd.commands.GeneratePlayersOrderCommand;
import br.ufrn.imd.cambio_imd.commands.ICommand;
import br.ufrn.imd.cambio_imd.commands.PlayerDiscardCardOnPileCommand;
import br.ufrn.imd.cambio_imd.commands.PlayerDrawCardFromPileCommand;

// Definir operações de jogo AQUI!!
public interface IGameMediator {
    void executeAction(ICommand command);
    void generateOrder(GeneratePlayersOrderCommand command);
    void playerDrawCard(PlayerDrawCardFromPileCommand command);
    void playerDiscardCard(PlayerDiscardCardOnPileCommand command);

}
