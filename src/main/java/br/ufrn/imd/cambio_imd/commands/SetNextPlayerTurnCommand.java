package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class SetNextPlayerTurnCommand implements ICommand {
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();

        var it = context.getPlayers().getData().iterator();
        Player player = null;
        while (it.hasNext())
            if (it.next().getId() == context.getCurrentPlayer().getId()) {
                player = it.next();
                break;
            }

        if (player != null)
            context.setCurrentPlayerIndex(player.getId());
    }
}
