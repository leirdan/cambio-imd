package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.controllers.GameController;
import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;

public class StartGameCommand implements ICommand{

    @Override
    public void execute() {
        var gm = GameManager.getInstance();
        gm.init();

        var sm = ScreenManager.getInstance();
        GameController controller = sm.getLoader(Screen.GAME).getController();
        controller.render();

    }
}
