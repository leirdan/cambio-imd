package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.commands.*;
import br.ufrn.imd.cambio_imd.controllers.GameController;
import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

/**
 *
 */
public class GameManager {
    private GameContext context = GameContext.getInstance();
    private static GameManager instance;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

    public void start() throws UnitializedGameException {
        if (context.getCardsPerHandLimit() == 0) {
            throw new UnitializedGameException("O jogo não foi inicializado corretamente. " +
                    "Certifique-se de chamar todos os métodos de setup antes deste.");
        }

        new DealCardsCommand().execute();
        new CreatePlayersCommand().execute();
        new GiveCardsToPlayersCommand().execute();
        new GeneratePlayersOrderCommand().execute();

        // Isso aqui é necessário pra trocar do menu para o jogo de fato de forma adequada, com todos os dados configurados.
        var sm = ScreenManager.getInstance();
        GameController controller = sm.getLoader(Screen.GAME).getController();
        controller.render();
    }


    public void setupGameMode(ActionEvent event) {
        new SetGameModeCommand(event).execute();
    }
}
