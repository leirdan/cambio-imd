package br.ufrn.imd.cambio_imd.dao;

import br.ufrn.imd.cambio_imd.enums.Round;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

/**
 * Classe que representa o estado do jogo, contendo informações sobre os jogadores, cartas e pilhas.
 * Concentra todos os dados necessários para que outras classes possam acessar e modificar o estado do jogo.
 * 
 * Possui informações como:
 * @see Players
 * @see DrawPile
 * @see DiscardPile
 */
public class GameDAO {
    /**
     * Atributo que inicializa a classe de jogadores. 
     * */    
    private PlayersDAO playersDAO = new PlayersDAO(); //< Lista de jogadores

    /**
     * Variável que armazena o atual jogador na ordem cronológica original do jogo (Acesso de avanço de ordem de jogo, por isso um inteiro)
     */
    private int currentPlayerIndex = 0;
    /**
     * Variável que armazena o índice primeiro jogador que jogou na rodada.
     */
    private int firstPlayerIndex = 0;
    /*
     * Variável que armazena o indíce do jogador que solicitou o câmbio.
     * Inicializado em -1 pois vetores não tem índices negativos.
     */
    private int playerThatAskedCambioIndex = -1;

    /**
     * Variável que armazena o jogador vencedor.
     */
    private Player winner = null; 

    /**
     * Variável que armazena o tipo de rodada que está ocorrendo.
     */
    private Round roundType = Round.CUT;


    /**
     * Retorna o índice do primeiro jogador que jogou na rodada.
     * @return retorna o índice do primeiro jogador que jogou na rodada.
     */
    public int getFirstPlayerIndex() {
        return firstPlayerIndex;
    }

    /**
     * Define o índice do primeiro jogador que jogou na rodada.
     */
    public void setFirstPlayerIndex(int newFirstPlayerIndex) {
        this.firstPlayerIndex = newFirstPlayerIndex;
    }

    
    //<Informações sobre as cartas

    /**
     * Pilha de reposição, onde os jogadores puxam as cartas
     */
    private DrawPile drawPile = new DrawPile();

    /**
     * Pilha central, onde os jogadores jogam as cartas
     */
    private DiscardPile discardPile = new DiscardPile();

    //< Informações sobre a mão dos jogadores

    /**
     * Quantas cartas a mão de cada jogador terá
     */
    private int cardsPerHandLimit = 0;

    /**
     * Quantas cartas os jogadores poderão ver inicialmente
     */
    private int revealedCardsLimit = 0;

    /**
     * Variável que armazena a instância da classe GameDAO.
     * Daqui seguimos um padrão de projeto Singleton, onde garantimos que só existirá uma instância dessa classe.
     */
    private static GameDAO instance = null;

    private GameDAO() {
    }

    /**
     * Retorna a instância da classe GameDAO.
     * @return retorna a instância da classe GameDAO.
     */
    public static GameDAO getInstance() {
        if (instance == null)
            instance = new GameDAO();

        return instance;
    }


    //< Métodos relacionados aos jogadores
    
    /**
     * Retorna a lista de jogadores.
     * @return retorna a lista de jogadores.
     */
    public PlayersDAO getPlayers() {
        return playersDAO;
    }

    /**
     * Define a lista de jogadores.
     * @param playersDAO lista de jogadores.
     */
    public void setPlayers(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    /**
     * Retorna o tipo de rodada que está ocorrendo.
     * @return retorna o tipo de rodada que está ocorrendo.
     */
    public Round getRoundType() {
        return this.roundType;
    }

    /**
     * Define o tipo de rodada que está ocorrendo.  
     * @param round tipo de rodada que está ocorrendo.
     */
    public void setRoundType(Round round) {
        this.roundType = round;
    }

    /**
     * Retorna o jogador atual.
     * @return retorna o jogador atual.
     */

    public Player getCurrentPlayer() {
        if (playersDAO.getData() == null || playersDAO.getData().isEmpty())
            throw new UnitializedGameException("PlayersDAO were not set!");

        int size = playersDAO.getData().size();
        if (currentPlayerIndex >= size)
            currentPlayerIndex = 0;

        var playersArray = playersDAO.getData().toArray(new Player[0]);
        return playersArray[currentPlayerIndex];
    }

    /**
     * Retorna o próximo jogador.
     * @return retorna o próximo jogador.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    /**
     * Define o próximo jogador.
     * @param currentPlayerIndex próximo jogador.
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    /**
     * Retorna o próximo jogador.
     * @param winner jogador vencedor.
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Retorna o jogador vencedor.
     * @return retorna o jogador vencedor.
     */
    public Player getWinner() {
        return this.winner;
    }

    
    //< Métodos relacionados ao gerenciamento de pilhas

    /**
     * Retorna a pilha de reposição.
     * @return retorna a pilha de reposição.
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }

    /**
     * Define a pilha de reposição.
     * @param drawPile pilha de reposição.
     */
    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    /**
     * Retorna a quantidade de cartas na pilha de reposição.
     * @return retorna a quantidade de cartas na pilha de reposição.
     */
    public int getDrawPileCount() {
        return this.drawPile.getAmount();
    }

    /**
     * Retorna a pilha central, onde os jogadores jogam as cartas.
     * @return retorna a pilha central, onde os jogadores jogam as cartas.
     */
    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    /**
     * Define a pilha central, onde os jogadores jogam as cartas.
     * @param discardPile pilha central, onde os jogadores jogam as cartas.
     */
    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    
    //< Métodos relacionados às configurações de jogo

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public int getCardsPerHandLimit() {
        return cardsPerHandLimit;
    }

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public void setCardsPerHandLimit(int cardsPerHandLimit) {
        this.cardsPerHandLimit = cardsPerHandLimit;
    }

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public void setRevealedCardsLimit(int revealedCardsLimit) {
        this.revealedCardsLimit = revealedCardsLimit;
    }

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
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

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public boolean hasAnyPlayerWithoutCards() {
        for (var p : playersDAO.getData()) {
            if (p.getHand().isEmpty())
                return true;
        }

        return false;
    }

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public int getPlayerThatAskedCambioIndex() {
        return playerThatAskedCambioIndex;
    }

    /**
     * Retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     * @return retorna a quantidade de cartas que os jogadores poderão ver inicialmente.
     */
    public void setPlayerThatAskedCambioIndex(int playerThatAskedCambioIndex) {
        this.playerThatAskedCambioIndex = playerThatAskedCambioIndex;
    }
}
