package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;

import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import java.util.Stack;


/**
 * Controlador que gerencia a principal view do jogo.
 */
public class GameController extends ControllerBase {
    @FXML
    private Label playerTurnLabel;

    @FXML
    private Label drawPileCountLabel;

    @FXML
    private GridPane playerHandGridPane;


    // TODO: verificar se essa é uma boa maneira mesmo de renderizar as informações de forma dinâmica
    public void render () {
        try {
            uiManager.renderMain(playerTurnLabel, drawPileCountLabel);
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
   }


    private void carregarMaoJogador(Player player){
        playerHandGridPane.getChildren().clear();
        Stack<Card> playerHand = player.getHand().getCards();

        for (int i = 0; i < playerHand.size(); i++) {
            // Criar um ImageView para cada carta
            ImageView cardImageView = new ImageView();
            cardImageView.setFitHeight(81.0);
            cardImageView.setFitWidth(50.0);
            cardImageView.setPreserveRatio(true);
            cardImageView.setPickOnBounds(true);

            // Definir a imagem para o ImageView
            // String cardImageUrl = playerHand.get(i).getImageUrl();
            // cardImageView.setImage(new Image(cardImageUrl));

            // Adicionar o ImageView à GridPane na coluna correspondente
            playerHandGridPane.add(cardImageView, i, 0);
        }
    }
}