package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;
import br.ufrn.imd.cambio_imd.models.players.Player;

import java.util.*;

public class GeneratePlayersOrderCommand implements ICommand {
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();

        // fixme: uma certa gambiarra aqui e desperdício de memória
        List<Player> list = new ArrayList<>();
        for (var player : context.getPlayers().getData()) {
            list.add(player);
        }
        Collections.shuffle(list);

        context.getPlayers().setData(new LinkedHashSet<>(list));
    }
}
