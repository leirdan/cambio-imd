package br.ufrn.imd.cambio_imd.managers;

import br.ufrn.imd.cambio_imd.CambioApplication;
import br.ufrn.imd.cambio_imd.enums.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que gerencia as telas do jogo.
 * <p>
 * A classe é responsável por adicionar e trocar as telas do jogo, além de manter um mapeamento
 * entre as telas e suas respectivas cenas.
 * </p>
 * <p>
 * A classe é um Singleton, garantindo que apenas uma instância da classe seja criada.
 */
public class ScreenManager {

    /**
     * Atributo que inicializa a tela principal.
     */
    private Stage primaryScreen;

    /**
     * Atributo que inicializa o mapeamento entre as telas e suas respectivas cenas.
     */
    private Map<Screen, Scene> screens;

    /**
     * Atributo que inicializa a instância da classe.
     */
    private static ScreenManager instance;

    /**
     * Construtor privado da classe.
     */
    private ScreenManager() {
        screens = new HashMap<>();
    }

    // Método Singleton

    /**
     * Método para obter a instância da classe.
     * @return instância da classe.
     */
    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();

        return instance;
    }

    // Métodos relacionados à inicialização

    /**
     * Método para inicializar a tela principal.
     * @param stage tela principal.
     */
    public void init(Stage stage) {
        this.primaryScreen = stage;
    }

    // Métodos relacionados à adição e mudança de telas

    /**
     * Método para adicionar uma tela.
     * @param screen tela a ser adicionada.
     * @throws IOException exceção de entrada e saída.
     */
    public void add(Screen screen) throws IOException {
        var loader = new FXMLLoader(CambioApplication.class.getResource(screen.getUrl()));
        var newScene = new Scene(loader.load());

        screens.put(screen, newScene);
    }

    /**
     * Método para trocar a tela.
     * @param screen tela a ser trocada.
     */
    public void change(Screen screen) {
        var sceneToChange = screens.get(screen);
        if (sceneToChange != null)
            primaryScreen.setScene(sceneToChange);
    }

}
