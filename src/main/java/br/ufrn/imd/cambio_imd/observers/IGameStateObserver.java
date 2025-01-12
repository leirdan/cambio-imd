package br.ufrn.imd.cambio_imd.observers;

public interface IGameStateObserver {
    void onStart();
    void onAction(String message);
    void onChangeTurn();
    void onSuperCardDetected(int hintsNumber); //< Registrar 
}
