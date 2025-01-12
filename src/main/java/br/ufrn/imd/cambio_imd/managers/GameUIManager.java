package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;

public class GameUIManager {
    private double cardWidth;
    private double cardHeight;
    private int clickedCard;
    private Point2D discardPaneCoords = new Point2D(175, 12);
    private int remainingHints = 0;
    private static GameUIManager instance;
    private ArrayList<String> history = new ArrayList<>();

    private GameContext context = GameContext.getInstance();

    private GameUIManager() {
    }

    public double getCardWidth() {
        return cardWidth;
    }

    public Point2D getDiscardPaneCoords() {
        return discardPaneCoords;
    }

    public void setDiscardPaneCoords(double x, double y) {
        discardPaneCoords = new Point2D(x, y);
    }

    public void setCardWidth(double cardWidth) {
        this.cardWidth = cardWidth;
    }

    public double getCardHeight() {
        return cardHeight;
    }

    public void setCardHeight(double cardHeight) {
        this.cardHeight = cardHeight;
    }

    public static GameUIManager getInstance() {
        if (instance == null)
            instance = new GameUIManager();

        return instance;
    }

    public void addMessageOnHistory(String msg) {
        String instant = getFormattedInstant();
        this.history.add("[" + instant + "]: " + msg + "\n");
    }
    public ArrayList<String> getHistory() {
        return this.history;
    }

    public String getDrawPileCountText() {
        return "Restam " + context.getDrawPile().getCards().size() + " cartas.";
    }

    public String getFormattedInstant() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = dt.format(formatter);
        return formattedTime;
    }


    public int getClickedCard() {
        return clickedCard;
    }

    public void setClickedCard(int clickedCard) {
        this.clickedCard = clickedCard;
    }

    public int getRemainingHints() {
        return remainingHints;
    }

    public void setRemainingHints(int remainingHints) {
        this.remainingHints = remainingHints;
    }
}
