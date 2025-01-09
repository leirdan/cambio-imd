package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

import java.util.LinkedHashSet;

public class CreatePlayersCommand implements ICommand {
    @Override
    public void execute() {
        GameContext context = GameContext.getInstance();

        var players = new Players();
        players.addPlayer(new Player("Jogador"));
        players.addPlayer(new Player("Bot 1"));

        context.setPlayers(players);
    }
}
