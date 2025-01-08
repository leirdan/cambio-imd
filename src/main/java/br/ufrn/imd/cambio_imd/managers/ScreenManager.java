package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.CambioApplication;
import br.ufrn.imd.cambio_imd.enums.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private Stage primaryScreen;
    private Map<Screen, Scene> screens;
    private static ScreenManager instance;

    private ScreenManager() {
        screens = new HashMap<>();
    }

    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();

        return instance;
    }

    public void init(Stage stage) {
        this.primaryScreen = stage;
    }

    public void add(Screen screen) throws IOException {
        var loader = new FXMLLoader(CambioApplication.class.getResource(screen.getUrl()));
        var newScene = new Scene(loader.load());

        screens.put(screen, newScene);
    }

    public void change(Screen screen) {
        var sceneToChange = screens.get(screen);
        if (sceneToChange != null)
            primaryScreen.setScene(sceneToChange);
    }

}
