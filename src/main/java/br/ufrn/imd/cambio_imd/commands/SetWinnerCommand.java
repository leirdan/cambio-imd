package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.models.players.*;
import java.util.LinkedHashSet;

public class SetWinnerCommand implements ICommand {
    private boolean isCut;
    public SetWinnerCommand(boolean isCut) {
        this.isCut = isCut;
    }

    @Override
    public void execute() {
        var context = GameDAO.getInstance();
        if(context.getCurrentPlayerIndex() == context.getPlayerThatAskedCambioIndex() && !isCut){
            LinkedHashSet<Player>players = context.getPlayers().getData();
            Player winner = players.iterator().next();
            for (Player player : players){
                if(player.getHand().computePoints() < winner.getHand().computePoints()){
                    winner = player;
                } else if (player.getHand().computePoints() == winner.getHand().computePoints()){
                    if(player.getHand().getCards().size() < winner.getHand().getCards().size()){
                        winner = player;
                    }
                }
            }
        }
        else {
            LinkedHashSet<Player>players = context.getPlayers().getData();
            for (Player player : players) {
                if(player.getHand().getCards().size() == 0){
                    context.setWinner(player);
                    break;
                }
            }
        }
    }
}
