package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class SuperCardCommand implements ICommand{
    @Override
    public void execute() {
        var context = GameContext.getInstance();
        var topCard = context.getDiscardPile().getCards().peek();

        if(topCard.isSuper()){
            var lastPlayerId = context.getLastPlayerToPlayId();       
            var optionalPlayer = context.getPlayers().findById(lastPlayerId);
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                if(topCard.getValue() == 11){
                    player.setHints(1);
                } else if(topCard.getValue() >= 12){
                    player.setHints(2);
                }
            }
        }
    }
}
