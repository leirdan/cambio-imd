package br.ufrn.imd.cambio_imd.managers;

import javafx.geometry.Point2D;
import br.ufrn.imd.cambio_imd.dao.GameDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UIManager {
    private double cardWidth;
    private double cardHeight;
    private int clickedCard;
    private Point2D discardPaneCoords = new Point2D(175, 12);
    private int remainingHints = 0;
    private static UIManager instance;
    private ArrayList<String> history = new ArrayList<>();

    private GameDAO context = GameDAO.getInstance();

    private UIManager() {
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

    public static UIManager getInstance() {
        if (instance == null)
            instance = new UIManager();

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
