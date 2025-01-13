package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;

public class AskForCambioCommand implements ICommand {    
    @Override
    public void execute(){
        GameDAO context = GameDAO.getInstance();
        context.setPlayerThatAskedCambioIndex(context.getCurrentPlayerIndex());
    }
}
