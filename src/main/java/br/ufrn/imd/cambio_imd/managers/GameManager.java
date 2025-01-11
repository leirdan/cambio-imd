package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.*;
import br.ufrn.imd.cambio_imd.controllers.GameController;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.enums.Round;
import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.observers.IGameAnimationObserver;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import javafx.event.ActionEvent;

import java.util.HashSet;
import java.util.Random;
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

    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }

    public boolean isCurrentPlayerHuman() {
        return context.getCurrentPlayer().isHuman();
    }

    public void handleBotAction(int percentage) {
        if (isCurrentPlayerHuman()) {
            return;
        }
        // Se tiver as condições corretas, peça câmbio
        // Se não, só joga
        // Por último passa a vez

        // e algumas outras condições...
        if (percentage <= 15) {
            callCambio();
        } else if (percentage > 15 && percentage <= 80) {
            int limit = getCurrentPlayerCards().size();
            int cardIndex = new Random().nextInt(limit);
            playCard(cardIndex);
        } else {
            skipTurn();
        }
    }

    public void skipTurn() {
        notifyAction(getCurrentPlayerName() + " passou a vez!");
        // seta o id pro próximo player
        advanceTurn();
    }

    public void callCambio() {
        // lógico de pedir cambio
        notifyAction(getCurrentPlayerName() + " pediu câmbio!");
        advanceTurn();
    }

    /*
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
     */
    public void playCard(int cardIndex) {
        // se está em turno de cortes, faça as verificações e qualquer coisa pule pro próximo.
        if (context.getRoundType() == Round.CUT) {

        }

        // se não está, então jogue normalmente.
        var player = context.getCurrentPlayer();
        var cardStr = getCurrentPlayerCards().get(cardIndex).toString();
        var drawPile = context.getDrawPile();
        var discardPile = context.getDiscardPile();

        new PlayerDiscardCardOnPileCommand(player, discardPile, cardIndex).execute();
        notifyCardDiscarded();
        new PlayerDrawCardFromPileCommand(player, drawPile).execute();
        notifyCardDrawn();

        notifyAction(getCurrentPlayerName() + " jogou carta " + cardStr);

        advanceTurn();
    }

    private void advanceTurn() {
        int index = context.getCurrentPlayerIndex();
        if (index >= context.getPlayers().getData().size()) {
            index = 0;
        }

        context.setCurrentPlayerIndex(++index);
        notifyChangeTurn();
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
