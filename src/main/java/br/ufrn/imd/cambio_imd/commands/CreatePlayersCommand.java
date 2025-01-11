package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.enums.PlayerType;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

import java.util.LinkedHashSet;

public class CreatePlayersCommand implements ICommand {
    @Override
    public void execute() {
        GameContext context = GameContext.getInstance();

        var players = new Players();
        players.addPlayer(new Player("Jogador", PlayerType.HUMAN));
        players.addPlayer(new Player("Bot 1", PlayerType.ROBOT));
        // FIXME: dando problema de indice, parece que esgota todas as cartas antes de terminar de deistirbuir
        // players.addPlayer(new Player("Bot 2", PlayerType.ROBOT));
        // players.addPlayer(new Player("Bot 3", PlayerType.ROBOT));

        context.setPlayers(players);
    }
}
