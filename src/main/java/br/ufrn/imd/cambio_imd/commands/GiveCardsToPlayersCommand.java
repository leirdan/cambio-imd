package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

import java.util.LinkedHashSet;

public class GiveCardsToPlayersCommand implements ICommand {
    @Override
    public void execute() {
        GameContext context = GameContext.getInstance();
        Players players =  context.getPlayers();
        LinkedHashSet<Player> updatedPlayers = new LinkedHashSet<>();

        for (var p: players.getData()) {
            for (int i = 0; i < context.getCardsPerHandLimit(); i++) {
                p.addCard(context.getDrawPile().removeCard(i));
            }
            updatedPlayers.add(p);
        }

        players.setData(updatedPlayers);
    }
}
