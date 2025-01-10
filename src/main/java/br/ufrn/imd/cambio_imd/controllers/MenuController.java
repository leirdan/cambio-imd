package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controlador do menu de início e de escolha do modo de jogo.
 */
public class MenuController extends ControllerBase {
    @FXML
    private Button startBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button sixCardsBtn;

    @FXML
    private Button eightCardsBtn;

    @FXML
    private Button twelveCardsBtn;

    @FXML
    void handleStartBtnClick() {
        screenManager.change(Screen.GAME_MODE);
    }

    @FXML
    void handleGameModeBtnClick(ActionEvent event) {
        screenManager.change(Screen.GAME);
        gameManager.setupGameMode(event);
        gameManager.start();
    }

    @FXML
    void handleExitBtnClick() {
        Platform.exit();
    }
}
