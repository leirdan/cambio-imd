package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.CardHand;

public class SuperCardCommand implements ICommand{
    @Override
    public void execute() {
        var context = GameContext.getInstance();
        var topCard = context.getDiscardPile().getCards().peek();

        if(topCard.isSuper()){
            var lastPlayerId = context.getCurrentPlayerIndex(); // Pegamos o player do contexto em que ele está inserido
            var optionalPlayer = context.getPlayers().findById(lastPlayerId);
            
            // Vemos se a carta que ele jogou no topo é uma carta especial e adicionamos o caso em que ele pode ver 
            // as cartas
            if (optionalPlayer.isPresent()) {
                CardHand hand = optionalPlayer.get().getHand();
                if(topCard.getValue() == 11){
                    
                } else if(topCard.getValue() >= 12){
                    // hand.setHints(2);
                }
            }
        }
    }
}
