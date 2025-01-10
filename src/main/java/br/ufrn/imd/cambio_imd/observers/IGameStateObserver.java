package br.ufrn.imd.cambio_imd.observers;

import javafx.event.ActionEvent;

public interface IGameStateObserver {
    void onStart();
    void onAction(String actionMessage);
    void onTurnChange();
}
