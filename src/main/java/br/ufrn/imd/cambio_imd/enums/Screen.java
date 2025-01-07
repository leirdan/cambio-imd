package br.ufrn.imd.cambio_imd.enums;

public enum Screen {
    MENU("views/screen_cambio_main_menu.fxml"),
    GAME_MODE("views/screen_cambio_game_mode.fxml"),
    GAME("views/screen_cambio_lightning_match.fxml");

    private final String url;

    Screen(String screenUrl) {
        this.url = screenUrl;
    }

    public String getUrl() {
        return this.url;
    }

}
