package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.UIManager;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;

/**
 * Controlador que serve como base para os outros.
 * <p>O controlador será a camada que captura as interações do usuário e envia para a camada de
 * gerenciadores, para que estes tratem dos dados e modifiquem o estado do jogo.</p>
 */
public abstract class ControllerBase {
    /**
     * Atributo que inicializa a classe de gerenciamento do jogo.
     */
    protected GameManager gameManager = GameManager.getInstance();

    /**
     * Atributo que inicializa a classe de gerenciamento de telas.
     */
    protected ScreenManager screenManager = ScreenManager.getInstance();

    /**
     * Atributo que inicializa a classe de gerenciamento de interface.
     */
    protected UIManager uiManager = UIManager.getInstance();
}
