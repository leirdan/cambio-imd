package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

import java.util.*;

public class GeneratePlayersOrderCommand implements ICommand {
    private LinkedHashSet<Player> players;

    public GeneratePlayersOrderCommand(LinkedHashSet<Player> players) {
        this.players = players;
    }

    @Override
    public void execute() {
        // TODO: validar se está ordenando
        List<Player> playersList = new ArrayList<>(players);
        Collections.shuffle(playersList);
        players = new LinkedHashSet<>(playersList);
    }
}
