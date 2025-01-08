package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.models.players.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameController {

    @FXML
    private Label playerTurnLabel;

    @FXML
    private Label drawPileCountLabel;

    @FXML
    private HBox playerCardsHBox;

    public void render () {
        try {
            var gm = GameManager.getInstance();

            Player currentPlayer = gm.getCurrentPlayer();
            if (currentPlayer == null)
                playerTurnLabel.setText("Turno não definido");
            else
                playerTurnLabel.setText("Turno de " + currentPlayer.getName());

            // FIXME: ver porque não está distribuindo as cartas
            drawPileCountLabel.setText("Restam: " + gm.getDrawPileCount());

        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }
}