package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.managers.GameManager;
import br.ufrn.imd.cambio_imd.managers.GameUIManager;
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
            GameContext gm = GameContext.getInstance();
            GameUIManager gim = GameUIManager.getInstance();
            switch (btn.getId()) {
                case "sixCardsBtn":
                    gm.setCardsPerHandLimit(6);
                    gm.setRevealedCardsLimit(1);
                    gim.setCardWidth(70);
                    gim.setCardHeight(90);
                    break;

                case "twelveCardsBtn":
                    gm.setCardsPerHandLimit(12);
                    gm.setRevealedCardsLimit(1);
                    gim.setCardWidth(60);
                    gim.setCardHeight(90);
                    break;

                case "eightCardsBtn":
                default:
                    gm.setCardsPerHandLimit(8);
                    gm.setRevealedCardsLimit(2);
                    gim.setCardWidth(70);
                    gim.setCardHeight(90);
                    break;
            }
        }
    }
}
