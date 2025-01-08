package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.GeneratePlayersOrderCommand;
import br.ufrn.imd.cambio_imd.commands.ICommand;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;
import br.ufrn.imd.cambio_imd.utility.DeckGenerator;

import java.util.LinkedHashSet;

public class GameManager {
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

    private static GameManager instance = null;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

    public Player getCurrentPlayer() {
        if (players.getPlayers() == null || players.getPlayers().isEmpty())
            throw new UnitializedGameException("Players were not set!");

        int size = players.getPlayers().size();
        if (currentPlayerIndex >= size)
            currentPlayerIndex = 0;

        var playersArray = players.getPlayers().toArray(new Player[0]);
        return playersArray[currentPlayerIndex];
    }

    public void init() {
        // FIXME: Por enquanto só 2 jogadores, depois mudar pra 4

        // Cria baralho
        dealCards();

        // Cria jogadores
        var players = new LinkedHashSet<Player>();
        players.add(new Player("Jogador"));
        players.add(new Player("Bot 1"));

        // Distribui cartas
        for (var p : players) {
            for (int i = 0; i < cardsPerHandLimit; i++) {
                p.addCard(this.drawPile.removeCard(i));
            }
            this.players.addPlayer(p);
        }

        // Sorteia ordem
        ICommand orderCom = new GeneratePlayersOrderCommand(this.players.getPlayers());
        orderCom.execute();
    }

    private void dealCards() {
        this.drawPile.setCards(DeckGenerator.generate());
        this.drawPile.shuffle();
    }


    public void setPlayers(Players players) {
        this.players = players;
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
