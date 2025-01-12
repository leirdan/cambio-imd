package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.*;
import br.ufrn.imd.cambio_imd.controllers.GameController;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.observers.IGameAnimationObserver;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import javafx.event.ActionEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GameManager {
    private GameContext context = GameContext.getInstance();
    private static GameManager instance;

    // Observers
    private Set<IGameAnimationObserver> animationObservers = new HashSet<>();
    private Set<IGameStateObserver> stateObservers = new HashSet<>();

    // Singleton pattern
    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    /* --- Métodos de controle do jogo --- */

    // Inicia o jogo, configurando e distribuindo cartas
    public void start() throws UnitializedGameException {
        if (context.getCardsPerHandLimit() == 0) {
            throw new UnitializedGameException("O jogo não foi inicializado corretamente. " +
                    "Certifique-se de chamar todos os métodos de setup antes deste.");
        }

        // Executa comandos de setup
        new DealCardsCommand().execute();
        new CreatePlayersCommand().execute();
        new GiveCardsToPlayersCommand().execute();
        new GeneratePlayersOrderCommand().execute();

        // Configura o descarte inicial
        var discardPile = context.getDiscardPile();
        var drawPile = context.getDrawPile();
        discardPile.addCard(drawPile.removeTopCard());

        notifyStartGame();
    }

    // Realiza o turno do jogador atual
    public void playTurn() {
        var currentPlayer = context.getCurrentPlayerToCut() != null 
                            ? context.getCurrentPlayerToCut() 
                            : context.getCurrentPlayer();
        var discardPile = context.getDiscardPile();
        var drawPile = context.getDrawPile();

        new VerifyWinnerCommand(context.getCurrentPlayer().getId()).execute();

        if (context.getCurrentPlayerToCut() != null) {
            // Jogada de corte
            new CallCutCommand(currentPlayer.getId()).execute();
            new PlayerDiscardCardOnPileCommand(currentPlayer, discardPile, currentPlayer.getCardIndex()).execute();
            notifyCardDiscarded();

            // Avalia a jogada de corte
            new CutCommand().execute();

            // Verifica as consequências da jogada de corte
            if (currentPlayer.isProhibitedCut() || currentPlayer.isWrongCut()) {
                new PlayerDrawCardFromPileCommand(currentPlayer, drawPile).execute();
                notifyCardDrawn();
            }

            // Reseta o estado do corte
            context.setCurrentPlayerToCut(null);

        } else {
            // Jogada normal
            new PlayerDrawCardFromPileCommand(currentPlayer, drawPile).execute();
            notifyCardDrawn();
            new PlayerDiscardCardOnPileCommand(currentPlayer, discardPile, currentPlayer.getCardIndex()).execute();
            notifyCardDiscarded();
        }

        new VerifyWinnerCommand(context.getCurrentPlayer().getId()).execute();
    }

    // Define o modo de jogo
    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }

    // Executa a jogada de descarte de carta
    public void playCard(int cardIndex) {
        var player = context.getCurrentPlayer();
        var drawPile = context.getDrawPile();
        var discardPile = context.getDiscardPile();

        new PlayerDiscardCardOnPileCommand(player, discardPile, cardIndex).execute();
        notifyCardDiscarded();
        new PlayerDrawCardFromPileCommand(player, drawPile).execute();
        notifyCardDrawn();

        if(getTopCardOnDiscardPile().isSuper()){
            notifySuperCardDetected();
        }
    }

    // Solicita cambio
    public void askForCambio(){
        new AskForCambioCommand(context.getCurrentPlayer().getId()).execute();
    }

    // Verifica se alguém ganhou
    public void verifyWin(){
        new VerifyWinnerCommand(context.getCurrentPlayer().getId()).execute();
    }

    /* --- Métodos de consulta de informações --- */

    public Stack<Card> getCurrentPlayerCards() {
        Player p = context.getCurrentPlayer();
        return p.getHand().getCards();
    }

    public Card getTopCardOnDiscardPile() {
        return context.getDiscardPile().getCardOnTop();
    }

    public String getCurrentPlayerName() {
        return context.getCurrentPlayer().getName();
    }

    public Player getWinner(){
        return context.getWinner();
    }

    /* --- Métodos de notificação para os observadores --- */

    private void notifyCardDrawn() {
        for (var observer : animationObservers) {
            observer.onCardDrawn();
        }
    }

    private void notifyCardDiscarded() {
        for (var observer : animationObservers) {
            observer.onCardDiscarded();
        }
    }

    private void notifyStartGame() {
        for (var observer : stateObservers) {
            observer.onStart();
        }
    }

    private void notifyAction(String actionMessage) {
        for (var observer : stateObservers) {
            observer.onAction(actionMessage);
        }
    }

    private void notifyChangeTurn() {
        for (var observer : stateObservers) {
            observer.onChangeTurn();
        }
    }

    private void notifyCambioAsked(){
        for (var observer : stateObservers) {
            observer.onCambioAsked();
        }
    }

    private void notifyWinner(){
        for (var observer : stateObservers) {
            observer.onWinner(context.getWinner().getId());
        }
    }

    private void notifySuperCardDetected(){
        for (var observer : stateObservers) {
            observer.onSuperCardDetected(context.getHintFromSuperCard(getTopCardOnDiscardPile()));
        }
    }

    /* --- Métodos de gerenciamento de observadores --- */

    public void addAnimationObserver(IGameAnimationObserver observer) {
        this.animationObservers.add(observer);
    }

    public void addStateObserver(IGameStateObserver observer) {
        this.stateObservers.add(observer);
    }
}
