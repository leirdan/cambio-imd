package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.models.players.*;

import java.util.LinkedHashSet;

public class SetWinnerCommand implements ICommand {
    private boolean isCut;

    public SetWinnerCommand() {
    }

    @Override
    public void execute() {
        var context = GameDAO.getInstance();
        LinkedHashSet<Player> players = context.getPlayers().getData();
        for (Player player : players) {
            if (player.getHand().getCards().size() == 0) {
                context.setWinner(player);
                break;
            }
        }

        // Se continua nulo, pega a menor quantidade de pontos
        if (context.getWinner() == null) {
            Player winner = players.iterator().next();

            for (Player player : players) {
                if (player.getHand().computePoints() < winner.getHand().computePoints()) {
                    winner = player;
                } else if (player.getHand().computePoints() == winner.getHand().computePoints()) {
                    if (player.getHand().getCards().size() < winner.getHand().getCards().size()) {
                        winner = player;
                    }
                }

            }
            context.setWinner(winner);
        }
    }
}
