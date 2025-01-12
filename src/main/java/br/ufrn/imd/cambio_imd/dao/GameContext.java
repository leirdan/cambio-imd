package br.ufrn.imd.cambio_imd.dao;

import br.ufrn.imd.cambio_imd.enums.Round;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;

public class GameContext {

    /**
     * Informações sobre os players
     */
    private Players players = new Players(); //< Lista de jogadores

    private int currentPlayerIndex = 0; //< Atual jogador na ordem cronológica original do jogo (Acesso de avanço de ordem de jogo, por isso um inteiro)
    private int firstPlayerIndex = 0; //< O primeiro jogador que jogou na rodada
    private int playerThatAskedCambio = -1;
    private Player winner = null; //< Jogador vencedor
    private Round roundType = Round.CUT;


    public int getFirstPlayerIndex() {
        return firstPlayerIndex;
    }

    public void setFirstPlayerIndex(int newFirstPlayerIndex) {
        this.firstPlayerIndex = newFirstPlayerIndex;
    }

    /**
     * Informações sobre as cartas
     */

    // Objetos de uso de fluxo de jogo
    private DrawPile drawPile = new DrawPile(); //< Pilha de reposição, onde os jogadores puxam as cartas
    private DiscardPile discardPile = new DiscardPile(); //< Pilha central, onde os jogadores jogam as cartas

    // Informações sobre a mão dos jogadores
    private int cardsPerHandLimit = 0; //< Quantas cartas a mão de cada jogador terá
    private int revealedCardsLimit = 0; //< Quantas cartas os jogadores poderão ver inicialmente

    private static GameContext instance = null;

    private GameContext() {
    }

    public static GameContext getInstance() {
        if (instance == null)
            instance = new GameContext();

        return instance;
    }

    /*
     * Métodos relacionados aos jogadores
     */
    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Round getRoundType() {
        return this.roundType;
    }

    public void setRoundType(Round round) {
        this.roundType = round;
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

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return this.winner;
    }

    /*
     * Métodos relacionados ao gerenciamento de pilhas
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public int getDrawPileCount() {
        return this.drawPile.getAmount();
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    /*
     * Métodos relacionados às configurações de jogo
     */
    public int getCardsPerHandLimit() {
        return cardsPerHandLimit;
    }

    public void setCardsPerHandLimit(int cardsPerHandLimit) {
        this.cardsPerHandLimit = cardsPerHandLimit;
    }

    public void setRevealedCardsLimit(int revealedCardsLimit) {
        this.revealedCardsLimit = revealedCardsLimit;
    }

    public int getHintFromSuperCard(Card card) {
        if (card.isSuper()) {
            if (card.getValue() == 11) {
                return 1;
            } else if (card.getValue() >= 12) {
                return 2;
            }
        }
        return 0;
    }

    public int getPlayerThatAskedCambio() {
        return playerThatAskedCambio;
    }

    public void setPlayerThatAskedCambio(int playerThatAskedCambio) {
        this.playerThatAskedCambio = playerThatAskedCambio;
    }
}
