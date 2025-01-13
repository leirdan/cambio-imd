package br.ufrn.imd.cambio_imd;

import br.ufrn.imd.cambio_imd.enums.Screen;
import br.ufrn.imd.cambio_imd.managers.ScreenManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.init(stage);

        for (var screen : Screen.values()){
            screenManager.add(screen);
        }

        screenManager.change(Screen.MENU);

        stage.setTitle("Cambio");
        stage.setWidth(1024);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}