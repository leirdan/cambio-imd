package br.ufrn.imd.cambio_imd.dao;

import br.ufrn.imd.cambio_imd.models.players.Player;

import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * Classe que representa o estado dos jogadores, contendo informações sobre os jogadores.
 * Concentra todos os dados necessários para que outras classes possam acessar e modificar o estado dos jogadores.
 * Possui objetos da classe @see Player.
 */
public class PlayersDAO {
    /**
     * Atributo que inicializa a classe de jogadores.
     */
    private LinkedHashSet<Player> data = new LinkedHashSet<>();

    /**
     * Retorna a lista de jogadores.
     * @return retorna a lista de jogadores.
     */
    public LinkedHashSet<Player> getData() {
        return data;
    }

    /**
     * Define a lista de jogadores.
     * @param data lista de jogadores.
     */
    public void setData(LinkedHashSet<Player> data) {
        this.data = data;
    }

    /**
     * Adiciona um jogador à lista de jogadores.
     * @param player jogador a ser adicionado.
     */
    public void addPlayer(Player player) {
        if (player == null) return;

        data.add(player);
    }

    /**
     * Remove um jogador da lista de jogadores.
     * @param player jogador a ser removido.
     */
    public void removePlayer(Player player) {
        if (player == null) return;

        data.remove(player);
    }

    /**
     * Retorna um jogador pelo seu id.
     * @param id id do jogador.
     * @return retorna um jogador pelo seu id.
     */
    public Optional<Player> findById(int id) {
        return data
                .stream()
                .filter(player -> player.getId() == id)
                .findFirst();
    }

    /**
     * Retorna o tamanho da lista de jogadores.
     * @return retorna o tamanho da lista de jogadores.
     */
    public int size() {
        return data.size();
    }
}
