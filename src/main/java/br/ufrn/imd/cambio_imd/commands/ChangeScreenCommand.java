package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;

public class ChangeScreenCommand implements ICommand {
    private Screen screen;

    public ChangeScreenCommand(Screen newScreen) {
        this.screen = newScreen;
    }

    @Override
    public void execute() {
        ScreenManager sm = ScreenManager.getInstance();
        sm.change(screen);
    }
}
