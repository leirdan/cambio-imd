package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;
import java.util.LinkedHashSet;

public class VerifyWinnerCommand implements ICommand {
    private int currentPlayerIndex = 0;

    public VerifyWinnerCommand(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    @Override
    public void execute() {

    }
        /*
        VerifyWinner();
        if(verifyIfCambioStarted()){
            verifyCambio();
        }
    }

    private void VerifyWinner(){

        GameContext context = GameContext.getInstance();
        LinkedHashSet<Player>playersList = context.getPlayers().getData();

        for (Player player : playersList) {
            if(player.getHand().getCards().isEmpty()){
                context.setWinner(player);
                break;
            }
        }
    }

         */

    /*
     * Se eu não tiver a ordem da partida não consigo aplicar o câmbio
     */
    /*
    private void verifyCambio(){
        GameContext context = GameContext.getInstance();
        LinkedHashSet<Player>playersList = context.getPlayers().getData();

        if(context.getPlayerToAskCambio() < 0){
            return;
        }

        Player player = new Player();
        
        for(Player p : playersList){
            if(player.getHand().getAmount() > player.getHand().getAmount()){
                if (p.getHand().getAmount() == player.getHand().getAmount()){
                    if(p.getHand().getCards().size() < player.getHand().getCards().size()){
                        player = p;
                    }
                } 
            } 
        }

        context.setWinner(player);
            
    }

    private boolean verifyIfCambioStarted(){
        var context = GameContext.getInstance();
        return currentPlayerIndex == context.getPlayerToAskCambio();
    }


     */
}
