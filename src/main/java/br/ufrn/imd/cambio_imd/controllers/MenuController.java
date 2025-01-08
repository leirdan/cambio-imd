package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 */
public class MenuController {
    private GameManager gameManager = GameManager.getInstance();
    private ScreenManager screenManager = ScreenManager.getInstance();

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
    void handleStartGame() {
        screenManager.change(Screen.GAME_MODE);
    }

    @FXML
    void handleGameModeSelect(ActionEvent event) {
        screenManager.change(Screen.GAME);
        gameManager.setupGameMode(event);
        gameManager.start();
    }

    @FXML
    void handleExitGame() {
        Platform.exit();
    }
}
