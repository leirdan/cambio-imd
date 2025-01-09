package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import javafx.scene.control.Label;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.players.Player;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

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

    public String getPlayerLabelText() {
        Player p = context.getCurrentPlayer();
        if (p == null)
            return "Turno não definido";
        else
            return "Turno de " + p.getName();
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

}
