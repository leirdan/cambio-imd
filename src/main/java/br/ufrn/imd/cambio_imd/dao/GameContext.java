package br.ufrn.imd.cambio_imd.dao;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

public class GameContext {

    /**
     *
     */
    private Players players = new Players();

    private int currentPlayerIndex = 0;


    /**
     *
     */
    private DrawPile drawPile = new DrawPile();

    /**
     *
     */
    private DiscardPile discardPile = new DiscardPile();

    /**
     * talvez melhorar esses nomes
     */
    private int cardsPerHandLimit = 0; //< Quantas cartas a mão de cada jogador terá
    private int revealedCardsLimit = 0; //< Quantas cartas os jogadores poderão ver inicialmente

    /*
     */
    private Player winner = null;

    private static GameContext instance = null;

    private GameContext() {
    }

    public static GameContext getInstance() {
        if (instance == null)
            instance = new GameContext();

        return instance;
    }

    public Player getCurrentPlayer() {
        if (players.getData() == null || players.getData().isEmpty())
            throw new UnitializedGameException("Players were not set!");

        int size = players.getData().size();
        if (currentPlayerIndex >= size)
            currentPlayerIndex = 0;

        var playersArray = players.getData().toArray(new Player[0]);
        return playersArray[currentPlayerIndex];
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public int getDrawPileCount() {
        return this.drawPile.getAmount();
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public int getCardsPerHandLimit() {
        return cardsPerHandLimit;
    }

    public void setCardsPerHandLimit(int cardsPerHandLimit) {
        this.cardsPerHandLimit = cardsPerHandLimit;
    }

    public void setRevealedCardsLimit(int revealedCardsLimit) {
        this.revealedCardsLimit = revealedCardsLimit;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
