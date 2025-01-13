package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.dao.PlayersDAO;
import br.ufrn.imd.cambio_imd.models.players.Player;

import java.util.LinkedHashSet;

public class GiveCardsToPlayersCommand implements ICommand {
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();
        PlayersDAO playersDAO =  context.getPlayers();
        LinkedHashSet<Player> updatedPlayers = new LinkedHashSet<>();

        for (var p: playersDAO.getData()) {
            for (int i = 0; i < context.getCardsPerHandLimit(); i++) {
                p.addCard(context.getDrawPile().removeCard(i));
            }
            updatedPlayers.add(p);
        }

        playersDAO.setData(updatedPlayers);
    }
}
