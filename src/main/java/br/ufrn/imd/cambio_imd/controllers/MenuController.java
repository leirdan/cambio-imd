package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.commands.ChangeScreenCommand;
import br.ufrn.imd.cambio_imd.commands.ICommand;
import br.ufrn.imd.cambio_imd.commands.SetGameModeCommand;
import br.ufrn.imd.cambio_imd.enums.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 */
public class MenuController {
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
        ICommand com = new ChangeScreenCommand(Screen.GAME_MODE);
        com.execute();
    }

    @FXML
    void handleGameModeSelect(ActionEvent event) {
        // A ideia de criar commands é separar melhor as partes:
        // O Controller não precisa saber que existe um Screen ou GameManager,
        // ou seja, a parte da UI fica "isolada" da parte lógica.
        ICommand setCom = new SetGameModeCommand(event);
        setCom.execute();

        ICommand changeCom = new ChangeScreenCommand(Screen.GAME);
        changeCom.execute();
    }

    @FXML
    void handleExitGame() {
        Platform.exit();
    }
}
