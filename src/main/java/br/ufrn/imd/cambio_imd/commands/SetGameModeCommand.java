package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.managers.GameManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class SetGameModeCommand implements ICommand {
    private ActionEvent event;

    public SetGameModeCommand(ActionEvent event) {
        this.event = event;
    }

    @Override
    public void execute() {
        if (event.getSource() instanceof Button btn) {
            GameManager gm = GameManager.getInstance();
            switch (btn.getId()) {
                case "sixCardsBtn":
                    gm.setCardsPerHandLimit(6);
                    gm.setRevealedCardsLimit(1);
                    break;

                case "eightCardsBtn":
                    gm.setCardsPerHandLimit(8);
                    gm.setRevealedCardsLimit(2);
                    break;

                case "twelveCardsBtn":
                    gm.setCardsPerHandLimit(12);
                    gm.setRevealedCardsLimit(1);
                    break;
            }
        }
    }
}
