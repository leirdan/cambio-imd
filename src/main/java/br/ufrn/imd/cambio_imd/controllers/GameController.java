package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controlador que gerencia a principal view do jogo.
 */
public class GameController extends ControllerBase {
    @FXML
    private Label playerTurnLabel;

    @FXML
    private Label drawPileCountLabel;

    @FXML
    private HBox playerCardsHBox;

    @FXML
    private TextArea messageBox;


    // TODO: verificar se essa é uma boa maneira mesmo de renderizar as informações de forma dinâmica
    public void render() {
        try {
            // playerTurnLabel.setText(uiManager.getPlayerLabelText());
            // drawPileCountLabel.setText(uiManager.getDrawPileCountText());
            print("Jogo iniciado");
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void print(String msg) {
        String instant = uiManager.getFormattedInstant();
        messageBox.appendText("[" + instant + "]: " + msg + "\n");
    }
}