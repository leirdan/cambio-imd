package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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

    // TODO: verificar se essa é uma boa maneira mesmo de renderizar as informações de forma dinâmica
    public void render () {
        try {
            uiManager.renderMain(playerTurnLabel, drawPileCountLabel);
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }
}