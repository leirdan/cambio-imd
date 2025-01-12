package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.enums.TransitionType;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.observers.IGameAnimationObserver;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import br.ufrn.imd.cambio_imd.utility.CardAssetMapper;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Stack;

/**
 * Controlador que gerencia a principal view do jogo.
 */
public class GameController extends ControllerBase {
    @FXML
    private TextField playerTextField;

    @FXML
    private Label drawPileCountLabel;

    @FXML
    private GridPane playerHandGridPane;

    @FXML
    private Pane pilesPane;

    @FXML
    private ImageView discardPileImage;

    @FXML
    private ImageView drawPileImage;

    @FXML
    private TextArea messageBox;

    private final Button playBtn = new Button();
    private final Button showBtn = new Button();

    private VBox optionsBox;

    @FXML
    protected void initialize() {
        // FIXME: documentar bem essa inicialização
        // Registrando observadores por classes anônimas
        gameManager.addAnimationObserver(new IGameAnimationObserver() {
            @Override
            public void onCardDrawn() {
                animateCardDrawn();
            }

            @Override
            public void onCardDiscarded() {
                animateCardDiscarded();
            }
        });

        gameManager.addStateObserver(new IGameStateObserver() {
            @Override
            public void onStart() {
                render();
            }

            @Override
            public void onAction(String message) {
                uiManager.addMessageOnHistory(message);
                renderHistory();
            }

            @Override
            public void onChangeTurn() {
                render();
            }

            @Override
            public void onSuperCardDetected(int hintsNumber){
                uiManager.addMessageOnHistory("Você pode ver " + hintsNumber + " cartas!");
                renderHistory();
                uiManager.setRemainingHints(hintsNumber);
                if (optionsBox != null && !optionsBox.getChildren().contains(showBtn)) {
                    optionsBox.getChildren().add(showBtn);
                }
            }

            @Override
            public void onCambioAsked() {
                uiManager.addMessageOnHistory("O jogador " + gameManager.getCurrentPlayerName() + " pediu um câmbio!");
                renderHistory();
                // Disso assumimos que, ao pedir um câmbio, o jogador ainda está dentro de sua rodada e isso não terpa
                // conflitos caso um novo jogador assumisse logo após ele pedir câmbio
            }

            @Override
            public void onWinner(int playerId) {
                uiManager.addMessageOnHistory("O jogador " + gameManager.getWinner().getName() + " venceu!");
                renderHistory();
            }
        });

        playBtn.setText("Jogar");
        playBtn.setOnMouseClicked(click -> handlePlayBtnClick());
        playBtn.setMinWidth(50);
        showBtn.setText("Ver");
        showBtn.setOnMouseClicked(click -> handleShowBtnClick());
        showBtn.setMinWidth(50);

        optionsBox = new VBox(5, playBtn);
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
            renderHistory();
            renderPlayerInfo();
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void renderPlayerInfo() {
        // playerTextField.setText(gameManager.getCurrentPlayerName());
        renderPlayerHand();
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

    @FXML
    protected void handleCardClick(MouseEvent event) {
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
        gameManager.playCard(uiManager.getClickedCard());
    }

    @FXML
    protected void handleShowBtnClick() {
        int cardIndex = uiManager.getClickedCard();
        
        ImageView cardImageView = (ImageView) playerHandGridPane.getChildren().get(cardIndex);

        var card = gameManager.getCurrentPlayerCards().get(cardIndex);

        Image frontImage = CardAssetMapper.getAsset(card); // Frente da carta
        Image backImage = CardAssetMapper.getBackCardAsset(); // Verso da carta

        animateCardFlip(cardImageView, frontImage, backImage);

        if (uiManager.getRemainingHints() > 0)
            uiManager.setRemainingHints(uiManager.getRemainingHints() - 1);

        if (optionsBox.getChildren().contains(showBtn) && uiManager.getRemainingHints() == 0) {
            optionsBox.getChildren().remove(showBtn);
        }
    }

    protected void renderHistory() {
        messageBox.clear();
        for (var message : uiManager.getHistory()) {
            messageBox.appendText(message);
        }
    }

    // Métodos de animação
    // TODO: será que isso é responsabilidade desta classe?

    // FIXME: deixar este método mais claro
    private void animateCardDiscarded() {
        var cardNode = playerHandGridPane.getChildren().get(uiManager.getClickedCard());

        applyTransition(cardNode, Duration.millis(500), TransitionType.FADE_OUT, () -> {
            pilesPane.getChildren().remove(discardPileImage);
            playerHandGridPane.getChildren().remove(cardNode);
            renderPlayerHand();

            Image cardImage = CardAssetMapper.getAsset(gameManager.getTopCardOnDiscardPile());

            ImageView discardImageView = new ImageView(cardImage);
            discardImageView.setFitWidth(uiManager.getCardWidth());
            discardImageView.setFitHeight(uiManager.getCardHeight());
            discardImageView.setLayoutX(uiManager.getDiscardPaneCoords().getX());
            discardImageView.setLayoutY(uiManager.getDiscardPaneCoords().getY());

            applyTransition(discardImageView, Duration.millis(500), TransitionType.FADE_IN);

            pilesPane.getChildren().add(discardImageView);
        });

    }

    private void animateCardDrawn() {
        var drawCardImage = drawPileImage;

        applyTransition(drawPileImage, Duration.millis(500), TransitionType.FADE_OUT, () -> {
            playerHandGridPane.getChildren().add(drawCardImage);
            renderPlayerHand();
            drawCardImage.setOpacity(1);
        });
    }

    /**
     * Aplica uma transição do tipo <code>FadeTransition</code> em um elemento.
     *
     * @param element
     * @param time
     * @param type
     */
    private void applyTransition(Node element, Duration time, TransitionType type) {
        applyTransition(element, time, type, null);
    }

    /**
     * Aplica uma transição do tipo <code>FadeTransition</code> em um elemento.
     *
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
    
    private void animateCardFlip(ImageView cardImageView, Image frontImage, Image backImage) {
        // Etapa 1: Achatar a carta (escalando horizontalmente para zero)
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), cardImageView);
        scaleOut.setFromX(1.0);
        scaleOut.setToX(0.0); // Achatar horizontalmente
    
        // Etapa 2: Alterar a imagem da carta no meio do flip
        scaleOut.setOnFinished(event -> {
            cardImageView.setImage(frontImage); // Troca para frente
            
            // Etapa 3: Expandir novamente a carta (restaurar escala horizontal)
            ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), cardImageView);
            scaleIn.setFromX(0.0);
            scaleIn.setToX(1.0); // Restaurar a largura
    
            scaleIn.setOnFinished(e -> {
                // Etapa 4: Pausar por 1.7 segundos antes de virar de volta
                PauseTransition pause = new PauseTransition(Duration.seconds(1.7));
                pause.setOnFinished(p -> {
                    // Etapa 5: Reverter a carta para o verso
                    ScaleTransition scaleOutBack = new ScaleTransition(Duration.millis(300), cardImageView);
                    scaleOutBack.setFromX(1.0);
                    scaleOutBack.setToX(0.0);
    
                    scaleOutBack.setOnFinished(ev -> {
                        cardImageView.setImage(backImage); // Retorna ao verso da carta
    
                        ScaleTransition scaleInBack = new ScaleTransition(Duration.millis(300), cardImageView);
                        scaleInBack.setFromX(0.0);
                        scaleInBack.setToX(1.0);
                        scaleInBack.play(); // Expande de volta ao estado original
                    });
    
                    scaleOutBack.play(); // Achatar novamente para virar de volta
                });
    
                pause.play(); // Executa a pausa de 1.7 segundos
            });
    
            scaleIn.play(); // Executa a expansão inicial
        });
    
        scaleOut.play(); // Inicia a animação de virada
    }

    
}