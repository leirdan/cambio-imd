package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;

public class AskForCambioCommand implements ICommand {    
    @Override
    public void execute(){
        GameContext context = GameContext.getInstance();
        context.setPlayerThatAskedCambio(context.getCurrentPlayerIndex());
    }
}
