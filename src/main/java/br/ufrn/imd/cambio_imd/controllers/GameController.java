package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.enums.TransitionType;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.utility.CardAssetMapper;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    private final Button playBtn = new Button();
    private final Button swapBtn = new Button();
    private VBox optionsBox;

    @FXML
    protected void initialize() {
        playBtn.setText("Jogar");
        playBtn.setOnMouseClicked(click -> handlePlayBtnClick());
        playBtn.setMinWidth(50);
        swapBtn.setText("Trocar");
        swapBtn.setOnMouseClicked(click -> handleSwapBtnClick());
        swapBtn.setMinWidth(50);

        optionsBox = new VBox(5, playBtn, swapBtn);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setStyle("""
                -fx-background-color: rgba(50, 50, 50, 0.9);
                -fx-border-color: white;
                -fx-border-width: 1px;
                -fx-border-radius: 5px;
                -fx-background-radius: 5px;
                -fx-padding: 10px;
                -fx-margin-bottom: 10px;
                -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1);
               """);

        // Registra ação de fechar a janela de opções caso clique fora
        playerHandGridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, click -> {
            Node source = (Node) click.getTarget();
            boolean clickedOutsideCard = playerHandGridPane.getChildren().stream()
                    .noneMatch(child -> child == source);

            if (clickedOutsideCard) {
                applyTransition(optionsBox, Duration.millis(300), TransitionType.FADE_OUT, () -> {
                    playerHandGridPane.getChildren().remove(optionsBox);
                });
            }
        });
    }


    public void render() {
        try {
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
        // Se estiver na 1ª linha aparece em cima da carta, senão, aparece abaixo.
        optionsBox.setTranslateY(row == 0 ? -50 : 50);
        applyTransition(optionsBox, Duration.millis(300), TransitionType.FADE_IN);

        playerHandGridPane.add(optionsBox, col, row);
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

        playerHandGridPane.getChildren().clear(); // Limpa as cartas anteriores
        Stack<Card> playerHand = gameManager.getCurrentPlayerCards();

        for (int i = 0; i < playerHand.size(); i++) {
            ImageView cardImageView = new ImageView();
            cardImageView.setFitHeight(uiManager.getCardHeight());
            cardImageView.setFitWidth(uiManager.getCardWidth());
            cardImageView.setPreserveRatio(true);
            cardImageView.setPickOnBounds(true);

            int index = i; // Precisa disso para não dar erro no parametro abaixo
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

    /**
     * Aplica uma transição do tipo <code>FadeTransition</code> em um elemento.
     * @param element
     * @param time
     * @param type
     */
    private void applyTransition(Node element, Duration time, TransitionType type) {
        applyTransition(element, time, type, null);
    }

    /**
     * Aplica uma transição do tipo <code>FadeTransition</code> em um elemento.
     * @param element
     * @param time
     * @param type
     * @param onFinish
     */
    private void applyTransition(Node element, Duration time, TransitionType type,
                                 Runnable onFinish) {
        FadeTransition transition = new FadeTransition(time, element);
        switch (type) {
            case FADE_IN -> {
                transition.setFromValue(0.0);
                transition.setToValue(1.0);
                break;
            }
            case FADE_OUT -> {
                transition.setFromValue(1.0);
                transition.setToValue(0.0);
                break;
            }
            default -> {
                break;
            }
        }

        if (onFinish != null) {
            transition.setOnFinished(event -> onFinish.run());
        }
        transition.play();
    }
}