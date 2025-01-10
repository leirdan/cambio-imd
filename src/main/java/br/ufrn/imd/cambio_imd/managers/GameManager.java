package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.*;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
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


    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }

    public void playCard(int cardIndex) {
        try {
            var player = context.getCurrentPlayer();
            var card = getCurrentPlayerCards().get(cardIndex).toString();

            var drawPile = context.getDrawPile();
            var discardPile = context.getDiscardPile();

            new PlayerDiscardCardOnPileCommand(player, discardPile, cardIndex).execute();
            notifyCardDiscarded();

            new PlayerDrawCardFromPileCommand(player, drawPile).execute();
            notifyCardDrawn();

            notifyAction(player.getName() + " jogou carta " + card);

            // TODO: Achar um jeito melhor de conseguir manter controle sobre qual o jogador da vez para renderizar

            // FIXME: isso aqui embaixo foi só um teste, não está funcionando
            var currentIndex = context.getCurrentPlayerIndex();
            // se o player da vez eh o ultimo volta pro primeiro, indice 0
            context.setCurrentPlayerIndex(currentIndex == context.getPlayers().getData().size() - 1 ? 0 :
                    currentIndex++);
            notifyChangeTurn();
        } catch (Exception ex) {
            notifyAction("Erro na operação!");
        }
    }

    /* Métodos que cadastram observadores */

    public void addAnimationObserver(IGameAnimationObserver observer) {
        this.animationObservers.add(observer);
    }

    public void addStateObserver(IGameStateObserver observer) {
        this.stateObservers.add(observer);
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
            observer.onTurnChange();
        }
    }
}
