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
 * <p>
 * Responsável por gerenciar as interações do usuário no menu inicial, incluindo
 * iniciar o jogo, selecionar o modo de jogo e sair do aplicativo.
 * </p>
 */
public class MenuController extends ControllerBase {

    /**
     * Botão para iniciar o jogo.
     */
    @FXML
    private Button startBtn;

    /**
     * Botão para sair do jogo.
     */
    @FXML
    private Button exitBtn;

    /**
     * Botão para selecionar o modo de jogo com 6 cartas.
     */
    @FXML
    private Button sixCardsBtn;

    /**
     * Botão para selecionar o modo de jogo com 8 cartas.
     */
    @FXML
    private Button eightCardsBtn;

    /**
     * Botão para selecionar o modo de jogo com 12 cartas.
     */
    @FXML
    private Button twelveCardsBtn;

    /**
     * Manipula o clique no botão "Iniciar".
     * <p>
     * Redireciona o usuário para a tela de seleção do modo de jogo.
     * </p>
     */
    @FXML
    void handleStartBtnClick() {
        screenManager.change(Screen.GAME_MODE);
    }

    /**
     * Manipula os cliques nos botões de seleção de modo de jogo.
     * <p>
     * Configura o modo de jogo com base no botão clicado, redireciona para a tela de jogo e inicia o jogo.
     * </p>
     *
     * @param event o evento acionado pelo clique no botão de modo de jogo.
     */
    @FXML
    void handleGameModeBtnClick(ActionEvent event) {
        screenManager.change(Screen.GAME);
        gameManager.setupGameMode(event);
        gameManager.start();
    }

    /**
     * Manipula o clique no botão "Sair".
     * <p>
     * Encerra a aplicação.
     * </p>
     */
    @FXML
    void handleExitBtnClick() {
        Platform.exit();
    }
}
