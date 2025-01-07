package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

public class GameManager {
    /**
     */
    private Players players;

    /**
     */
    private DrawPile drawPile;

    /**
     */
    private DiscardPile discardPile;

    /**
     * talvez melhorar esses nomes
     */
    private int cardsPerHandLimit = 0; //< Quantas cartas a mão de cada jogador terá
    private int revealedCardsLimit = 0; //< Quantas cartas os jogadores poderão ver inicialmente

    /*
     */
    private Player winner = null;

    private static GameManager instance = null;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
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
