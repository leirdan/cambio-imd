package br.ufrn.imd.cambio_imd.models.players;

import java.util.LinkedHashSet;
import java.util.Optional;

public class Players {
    private LinkedHashSet<Player> data = new LinkedHashSet<>();

    public LinkedHashSet<Player> getData() {
        return data;
    }

    public void setData(LinkedHashSet<Player> data) {
        this.data = data;
    }

    public void addPlayer(Player player) {
        if (player == null) return;

        data.add(player);
    }

    public void removePlayer(Player player) {
        if (player == null) return;

        data.remove(player);
    }

    public Optional<Player> findById(int id) {
        return data
                .stream()
                .filter(player -> player.getId() == id)
                .findFirst();
    }

    public int size() {
        return data.size();
    }
}
