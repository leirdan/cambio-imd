package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;

public class AskForCambioCommand implements ICommand {
    private int playerId;

    public AskForCambioCommand(int playerId){
        this.playerId = playerId;
    }
    
    @Override
    public void execute(){
        GameContext context = GameContext.getInstance();
        //context.setPlayerToAskCambio(playerId);
    }
}
