package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.dao.PlayersDAO;
import br.ufrn.imd.cambio_imd.enums.PlayerType;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class CreatePlayersCommand implements ICommand {
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();

        var players = new PlayersDAO();
        players.addPlayer(new Player("Jogador", PlayerType.HUMAN));
        players.addPlayer(new Player("Bot 1", PlayerType.ROBOT));
         players.addPlayer(new Player("Bot 2", PlayerType.ROBOT));
         players.addPlayer(new Player("Bot 3", PlayerType.ROBOT));

        context.setPlayers(players);
    }
}
