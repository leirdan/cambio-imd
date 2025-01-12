package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.*;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.enums.Round;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.observers.IGameAnimationObserver;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import br.ufrn.imd.cambio_imd.utility.RandomGenerator;
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
    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    /* --- Métodos de inicialização do jogo --- */

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
        new PutFirstCardOnDiscardPileCommand(context.getDiscardPile(), context.getDrawPile()).execute();

        notifyAction("Rodada de cortes!");
        notifyStartGame();
    }

    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }

    /* --- Métodos de controle de turnos e rodadas --- */

    public void resetPlayersRestrictions() {
        context.getPlayers().getData().forEach(player -> {
            player.setProhibitedCut(false);
            player.setWrongCut(false);
        });
    }

    public boolean isCurrentPlayerHuman(){
        return context.getCurrentPlayer().isHuman();
    }

    public void handleBotAction(int percentage) {
        if (isCurrentPlayerHuman()) return;

        Player bot = context.getCurrentPlayer();
        boolean canSkip = false;
        boolean canCallCambio = false;

        if (bot.getHand().getCards().size() < 5) {
            canCallCambio = true;
        }
        if (!isCurrentRoundNormal()) {
            canSkip = true;
        }

        if (percentage <= 10 && canCallCambio) {
            callCambio();
        } else if (percentage > 80 && percentage < 100 && canSkip) {
            skipTurn();
        } else {
            int limit = getCurrentPlayerCards().size();
            int cardIndex = RandomGenerator.getInt(limit);
            playCard(cardIndex);
        }
    }

    public void skipTurn() {
        notifyAction(getCurrentPlayerName() + " passou a vez!");
        advanceTurn();
    }

    public void callCambio() {
//        new AskForCambioCommand().execute();
        notifyAction(getCurrentPlayerName() + " pediu câmbio!");
        advanceTurn();
    }

    public void playCard(int cardIndex) {
        Player player = context.getCurrentPlayer();
        String cardStr = getCurrentPlayerCards().get(cardIndex).toString();
        DrawPile drawPile = context.getDrawPile();
        DiscardPile discardPile = context.getDiscardPile();

        // FIXME: atualmente, quando o jogador está bloqueado ele não é pulado automaticamente.
        // Ele precisa jogar a carta para receber a notícia. O legal é que fosse
        // Pulado automaticamente
        // pra isso, deve mexer no advanceTurn...

        if (context.getRoundType() == Round.CUT && player.isProhibitedCut()) {
            notifyAction(player.getName() + " está proibido de cortar!");
            advanceTurn();
        } else {
            new PlayerDiscardCardOnPileCommand(player, discardPile, cardIndex).execute();
            notifyAction(player.getName() + " jogou " + cardStr);
            notifyCardDiscarded();

            // Se for rodada de corte, verifica se o corte foi bem-sucedido.
            // Se não foi, puxa uma carta. Se foi, não precisa puxar.
            // Se não for rodada de corte, puxa normalmente.
            if (context.getRoundType() == Round.CUT) {
                new CheckCutCommand(player, discardPile).execute();
                if (player.isProhibitedCut() || player.madeWrongCut()) {
                    notifyAction(player.getName() + " cortou errado!");
                    new PlayerDrawCardFromPileCommand(player, drawPile).execute();
                    notifyCardDrawn();
                } else {
                    notifyAction(player.getName() + " cortou corretamente!");
                }
            } else {
                new PlayerDrawCardFromPileCommand(player, drawPile).execute();
                notifyCardDrawn();
            }
            if (getTopCardOnDiscardPile().isSuper()) {
                notifySuperCardDetected();
            }
            advanceTurn();
        }
    }

    public int getDrawPileCardsAmount() {
        return context.getDrawPileCount();
    }

    /**
     * Avança para o próximo jogador da lista, notificando
     * automaticamente a mudança de turno a todos os observadores.
     */
    private void advanceTurn() {
        int index = context.getCurrentPlayerIndex();
        int playerCount = context.getPlayers().getData().size();

        // Se o próximo jogador foi o primeiro da sequência, completou a rodada
        boolean hasCompletedRound = index + 1 == context.getFirstPlayerIndex();

        boolean hasAnyPlayerWithoutCards = context.hasAnyPlayerWithoutCards();
        // O round só deve ser normal quando for a vez do primeiro
        // jogador da rodada, depois está aberto pra cortes.
        if (context.getRoundType() == Round.CUT) {
            boolean hasPlayerThatAskedCambio = context.getPlayerThatAskedCambioIndex() != -1;

            if (hasAnyPlayerWithoutCards || (hasPlayerThatAskedCambio && index == context.getPlayerThatAskedCambioIndex())) {
                // TODO: implementar o ganhador
                // new SetWinnerCommand().execute();
            } else {
                if (hasCompletedRound) {
                    int nextFirstPlayerIndex = (context.getFirstPlayerIndex() + 1) % playerCount;
                    context.setFirstPlayerIndex(nextFirstPlayerIndex);
                    context.setRoundType(Round.NORMAL);
                    notifyAction("Rodada normal! ");
                }
            }
        } else if (context.getRoundType() == Round.NORMAL) {
            if (hasAnyPlayerWithoutCards)
                // new SetWinnerCommand().execute();
                context.setRoundType(Round.CUT);
            notifyAction("Rodada de cortes! ");
        }

        index = (index + 1) % playerCount;
        context.setCurrentPlayerIndex(index);
        notifyChangeTurn();
    }

    public void askForCambio() {
        new AskForCambioCommand().execute();
    }

    public void setWinner() {
        new SetWinnerCommand(context.getRoundType() == Round.CUT).execute();
    }

    /* --- Métodos de consulta de informações --- */

    public boolean isCurrentRoundNormal() {
        return context.getRoundType() == Round.NORMAL;
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

    public Player getWinner() {
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
        boolean hasWinner = context.getWinner() != null;
        if (hasWinner) {
            notifyWinner();
        } else {
            for (var observer : stateObservers) {
                observer.onChangeTurn();
            }
        }
    }

    private void notifyCambioAsked() {
        for (var observer : stateObservers) {
            observer.onCambioAsked();
        }
    }

    private void notifyWinner() {
        for (var observer : stateObservers) {
            observer.onWinner(context.getWinner().getId());
        }
    }

    private void notifySuperCardDetected() {
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
