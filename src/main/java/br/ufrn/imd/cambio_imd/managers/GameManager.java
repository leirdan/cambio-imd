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

/**
 *
 */
public class GameManager {
    private GameContext context = GameContext.getInstance();
    private static GameManager instance;
    private Set<IGameAnimationObserver> animationObservers = new HashSet<>();
    private Set<IGameStateObserver> stateObservers = new HashSet<>();

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }



    public void start() throws UnitializedGameException {
        if (context.getCardsPerHandLimit() == 0) {
            throw new UnitializedGameException("O jogo não foi inicializado corretamente. " +
                    "Certifique-se de chamar todos os métodos de setup antes deste.");
        }

        new DealCardsCommand().execute();
        new CreatePlayersCommand().execute();
        new GiveCardsToPlayersCommand().execute();
        new GeneratePlayersOrderCommand().execute();

        notifyStartGame();
    }

    public void playTurn() {
        var currentPlayer = context.getCurrentPlayerToCut() != null 
                            ? context.getCurrentPlayerToCut() 
                            : context.getCurrentPlayer();
        var discardPile = context.getDiscardPile();
        var drawPile = context.getDrawPile();

        new VerifyWinnerCommand(context.getCurrentPlayer().getId()).execute();

        if (context.getCurrentPlayerToCut() != null) {
            // Lógica de corte
            new CallCutCommand(currentPlayer.getId()).execute();
    
            new PlayerDiscardCardOnPileCommand(currentPlayer, discardPile, currentPlayer.getCardIndex()).execute();
            notifyCardDiscarded();
    
            // Avalia a jogada de corte
            new CutCommand().execute();
    
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


    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }

    public void playCard(int cardIndex) { // Pode ser obsoleto ou não.
        var player = context.getCurrentPlayer();
        var drawPile = context.getDrawPile();
        var discardPile = context.getDiscardPile();

        new PlayerDiscardCardOnPileCommand(player, discardPile, cardIndex).execute();
        notifyCardDiscarded();
        new PlayerDrawCardFromPileCommand(player, drawPile).execute();
        notifyCardDrawn();
    }

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

    /* Métodos que executam os observadores */

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
        System.out.println("Entrou em notify");
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

    public void addAnimationObserver(IGameAnimationObserver observer) {
        this.animationObservers.add(observer);
    }

    public void addStateObserver(IGameStateObserver observer) {
        this.stateObservers.add(observer);
    }
}
