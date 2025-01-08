package br.ufrn.imd.cambio_imd.models.players;

import java.util.LinkedHashSet;
import java.util.Optional;

public class Players {
    private LinkedHashSet<Player> players = new LinkedHashSet<>();

    public LinkedHashSet<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedHashSet<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (player == null) return;

        players.add(player);
    }

    public void removePlayer(Player player) {
        if (player == null) return;

        players.remove(player);
    }

    public Optional<Player> findById(int id) {
        return players
                .stream()
                .filter(player -> player.getId() == id)
                .findFirst();
    }
}
