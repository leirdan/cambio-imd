package br.ufrn.imd.cambio_imd.managers;

public class GameUIManager {
    private double cardWidth;
    private double cardHeight;
    private static GameUIManager instance;

    private GameUIManager() {
    }

    public double getCardWidth() {
        return cardWidth;
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
}
