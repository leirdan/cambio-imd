package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.GameUIManager;
import br.ufrn.imd.cambio_imd.models.players.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameController {
    private GameManager gameManager = GameManager.getInstance();
    private GameUIManager uiManager = GameUIManager.getInstance();

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