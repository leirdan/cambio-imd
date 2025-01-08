package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.GameUIManager;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;

/**
 * Controlador que serve como base para os outros.
 * <p>O controlador será a camada que captura as interações do usuário e envia para a camada de
 * gerenciadores, para que estes tratem dos dados e modifiquem o estado do jogo.</p>
 */
public abstract class ControllerBase {
    protected GameManager gameManager = GameManager.getInstance();
    protected ScreenManager screenManager = ScreenManager.getInstance();
    protected GameUIManager uiManager = GameUIManager.getInstance();
}
