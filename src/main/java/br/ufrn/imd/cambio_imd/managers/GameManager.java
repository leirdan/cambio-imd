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

    public void addAnimationObserver(IGameAnimationObserver observer) {
        this.animationObservers.add(observer);
    }

    public void addStateObserver(IGameStateObserver observer) {
        this.stateObservers.add(observer);
    }


    public Stack<Card> getCurrentPlayerCards() {
        Player p = context.getCurrentPlayer();
        return p.getHand().getCards();
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

    public void cutPlay(){
        var currentPlayer = context.getCurrentPlayerToCut();
        var discardPile = context.getDiscardPile();
        
        // Primeiramente, chamar o corte. O CallCut vai fazer com que o jogador a fazer o corte seja setado.
        // Esse não é um método de entrada. O método de entrada ainda deve ser inserido.
        new CallCutCommand(currentPlayer.getId()).execute();

        // Depois, deixamos o jogador fazer sua jogada. Este é o corte sendo realizado.
        new PlayerDiscardCardOnPileCommand(currentPlayer, discardPile, currentPlayer.getCardIndex());
        notifyCardDiscarded();

        // O jogo passa a avaliar a jogada do corte. para determinar se o jogador fez o corte correto ou não, e se pode 
        // continuar jogando ou não.
        new CutCommand().execute();

        if(currentPlayer.isProhibitedCut() || currentPlayer.isWrongCut()){
            new PlayerDrawCardFromPileCommand(context.getCurrentPlayerToCut(), context.getDrawPile()).execute();
            notifyCardDrawn();
        }

        context.setCurrentPlayerToCut(null);
    }
    
    public void normalPlay(){
        var currentPlayer = context.getCurrentPlayer();
        var discardPile = context.getDiscardPile();
        var drawPile = context.getDrawPile();

        new PlayerDrawCardFromPileCommand(currentPlayer, drawPile).execute();
        notifyCardDrawn();

        // Não sei como está sendo feita a lógica de integrar a interface gráfica aqui.

        new PlayerDiscardCardOnPileCommand(currentPlayer, discardPile, currentPlayer.getCardIndex()).execute();
        notifyCardDiscarded();
    }

    public void matchLoop(){
        boolean Encerrar = false; //< temos que ver como podemos ver a condição de parada
        // a ideia é fazer com que até todos os jogadores não possam mais cortar ou até que os jogadores não queiram mais, realizar verificação
    
        while(Encerrar){
            cutPlay();
        }

        new VerifyWinnerCommand(context.getCurrentPlayer().getId()).execute();
        while(context.getWinner() == null){
            while(context.getCurrentPlayerToCut() != null){
                cutPlay();
            }
            normalPlay();
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
}
