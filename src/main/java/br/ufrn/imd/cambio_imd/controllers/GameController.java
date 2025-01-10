package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.utility.CardAssetMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    @FXML
    private TextArea messageBox;

    private Button playBtn = new Button();
    private Button swapBtn = new Button();
    private HBox optionsBox;

    @FXML
    protected void initialize() {
        playBtn.setText("Jogar");
        playBtn.setOnMouseClicked(click -> handlePlayBtnClick());
        swapBtn.setText("Trocar");
        swapBtn.setOnMouseClicked(click -> handleSwapBtnClick());
        optionsBox = new HBox(5, playBtn, swapBtn); // Espaçamento de 5px
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 5px; -fx-border-radius: 5px;");
        optionsBox.setTranslateY(-50);
    }


    public void render() {
        try {
            // playerTurnLabel.setText(uiManager.getPlayerLabelText());
            // drawPileCountLabel.setText(uiManager.getDrawPileCountText());
            print("Jogo iniciado");
            renderPlayerHand();
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    protected void handleCardClick(MouseEvent event) {
        System.out.println("Clickou!!");
        int cardIndex = uiManager.getClickedCard();

        if (playerHandGridPane.getChildren().contains(optionsBox))
            playerHandGridPane.getChildren().remove(optionsBox);

        Node node = playerHandGridPane.getChildren().get(cardIndex);
        int col = GridPane.getColumnIndex(node);
        int row = GridPane.getRowIndex(node);
        playerHandGridPane.add(optionsBox, col, row);

        // Remove caixa ao clicar fora
        playerHandGridPane.setOnMouseClicked(click -> {
            if (click.getSource() != optionsBox){
                playerHandGridPane.getChildren().remove(optionsBox);
            }
        });
    }

    protected void handlePlayBtnClick() {
        System.out.println("Helloooo play");
    }

    protected void handleSwapBtnClick() {
        System.out.println("Helloooo swaaaap");
    }

    private void renderPlayerHand() {
        /**
         * Variáveis importantes de controle:
         * MAX_COLUMN: determina o índice máximo das colunas do gridPane, ou seja, são 6 colunas (índice 0 até 5)
         * correctRow: determina a linha que a carta será renderizada.
         * Até a 6ª carta ainda renderiza na 1ª linha, a partir daí, somente na 2ª..
         * correctColumn: determina a coluna que a carta será renderizada.
         * É calculado pelo índice i, e seu valor vai de 0 a 5.
         * Quando o índice i chega a 6, correctColumn volta ao valor 0.
         */
        final int MAX_COLUMN = 5;
        int correctRow = 0, correctColumn = 0;

        playerHandGridPane.getChildren().clear();
        // Não é pro controller acessar diretamente os dados do jogo, logo, pedimos ao manager a informação
        Stack<Card> playerHand = gameManager.getCurrentPlayerCards();

        for (int i = 0; i < playerHand.size(); i++) {
            ImageView cardImageView = new ImageView();
            cardImageView.setFitHeight(uiManager.getCardHeight());
            cardImageView.setFitWidth(uiManager.getCardWidth());
            cardImageView.setPreserveRatio(true);
            cardImageView.setPickOnBounds(true);

            int index = i; // Precisa disso para não dar erro no parametro abaixo
            // Verificar porque somente a primeira carta que eu clico exibe a seleção de opções e as outras não..
            cardImageView.setOnMouseClicked(mouseEvent -> {
                uiManager.setClickedCard(index);
                handleCardClick(mouseEvent);
            });

            Image cardImg = CardAssetMapper.getBackCardAsset();
            cardImageView.setImage(cardImg);

            correctColumn = i % (MAX_COLUMN + 1);
            if (i > MAX_COLUMN && correctRow == 0)
                correctRow++;

            playerHandGridPane.add(cardImageView, correctColumn, correctRow);
        }
    }

    private void print(String msg) {
        String instant = uiManager.getFormattedInstant();
        messageBox.appendText("[" + instant + "]: " + msg + "\n");
    }
}