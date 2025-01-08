package br.ufrn.imd.cambio_imd.managers;

import javafx.scene.control.Label;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class GameUIManager {
    private double cardWidth;
    private double cardHeight;
    private static GameUIManager instance;
    private GameContext context = GameContext.getInstance();

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

    public void renderMain(Label playerLabel, Label drawPileLabel) {
        Player p = context.getCurrentPlayer();
        if (p == null)
            playerLabel.setText("Turno não definido");
        else
            playerLabel.setText("Turno de " + p.getName());

        drawPileLabel.setText("Restam: " + context.getDrawPileCount());
    }

}
