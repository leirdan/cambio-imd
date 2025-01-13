package br.ufrn.imd.cambio_imd.controllers;

import br.ufrn.imd.cambio_imd.enums.TransitionType;
import br.ufrn.imd.cambio_imd.exceptions.UnitializedGameException;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.observers.IGameAnimationObserver;
import br.ufrn.imd.cambio_imd.observers.IGameStateObserver;
import br.ufrn.imd.cambio_imd.utility.CardAssetMapper;
import br.ufrn.imd.cambio_imd.utility.RandomGenerator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
 * Controla a interação do usuário com a interface, incluindo ações de jogadores humanos e robôs,
 * animações, exibição de informações do jogador, pilhas de cartas e histórico de ações.
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
    private ImageView discardPileImage, drawPileImage;
    @FXML
    private TextArea historyTextArea;
    @FXML
    private Button skipBtn, cambioBtn;
    private final Button playBtn = new Button(), showBtn = new Button();
    private VBox optionsBox;
    private Alert winnerAlert;

    @FXML
    protected void initialize() {
        initObservers();

        playBtn.setText("Jogar");
        playBtn.setOnMouseClicked(click -> handlePlayBtnClick());
        playBtn.setMinWidth(50);
        skipBtn.setOnMouseClicked(click -> handleSkipBtnClick());
        cambioBtn.setOnMouseClicked(click -> handleCambioBtnClick());
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

    protected void initObservers() {
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
                startTurn();
            }

            @Override
            public void onAction(String message) {
                uiManager.addMessageOnHistory(message);
                renderHistory();
            }

            @Override
            public void onChangeTurn() {
                render();
                startTurn();
            }

            @Override
            public void onSuperCardDetected(int hintsNumber) {
                uiManager.addMessageOnHistory(gameManager.getCurrentPlayerName() + " pode ver " + hintsNumber + " cartas!");
                renderHistory();
                if (gameManager.isCurrentPlayerHuman()) {
                    uiManager.setRemainingHints(hintsNumber);
                    if (optionsBox != null && !optionsBox.getChildren().contains(showBtn)) {
                        optionsBox.getChildren().add(showBtn);
                    }
                }
            }

            @Override
            public void onCambioAsked() {
                uiManager.addMessageOnHistory(gameManager.getCurrentPlayerName() + " pediu um câmbio!");
                renderHistory();
                // Disso assumimos que, ao pedir um câmbio, o jogador ainda está dentro de sua rodada e isso não tera
                // conflitos caso um novo jogador assumisse logo após ele pedir câmbio
            }

            @Override
            public void onWinner(int playerId) {
                uiManager.addMessageOnHistory("O " + gameManager.getWinner().getName() + " venceu!");
                renderHistory();
                renderWinnerAlert();
            }

            private void startTurn() {
                uiManager.addMessageOnHistory("Turno de: " + gameManager.getCurrentPlayerName());
                if (gameManager.isCurrentPlayerHuman()) {
                    enablePlayerControls();
                } else {
                    disablePlayerControls();
                    handleBotTurn();
                }
            }
        });
    }

    /**
     * Função que renderiza todas as informações da tela, incluindo
     * pilha de descarte de cartas, informações do jogador e outras.
     */
    public void render() {
        try {
            renderHistory();
            renderDiscardPile();
            renderPlayerInfo();
            renderDrawPileCount();
        } catch (UnitializedGameException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    /**
     * Captura o clique no botão de pedir câmbio e invoca a ação respectiva.
     */
    protected void handleCambioBtnClick() {
        gameManager.callCambio();
    }

    private void renderDrawPileCount() {
        drawPileCountLabel.setText("Restam " + gameManager.getDrawPileCardsAmount() + " cartas");
    }

    /**
     * Simula uma jogada de um bot.
     */
    private void handleBotTurn() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1 + RandomGenerator.getInt(3, 6)),
                event -> {
                    int percentage = RandomGenerator.getInt(100);
                    gameManager.handleBotAction(percentage);
                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void enablePlayerControls() {
        playBtn.setDisable(false);
        optionsBox.setDisable(false);
        cambioBtn.setDisable(false);

        skipBtn.setDisable(gameManager.isCurrentRoundNormal());
    }

    private void disablePlayerControls() {
        playBtn.setDisable(true);
        optionsBox.setDisable(true);
        cambioBtn.setDisable(true);
        skipBtn.setDisable(true);
    }

    @FXML
    private void handleSkipBtnClick() {
        gameManager.skipTurn();
    }

    /**
     * Renderiza o alerta informando quem foi o vencedor da vez.
     */
    private void renderWinnerAlert() {
        winnerAlert = new Alert(Alert.AlertType.INFORMATION);
        winnerAlert.setTitle("Fim de jogo");
        if (gameManager.isWinnerHuman()) {
            winnerAlert.setHeaderText("Parabéns!");
            winnerAlert.setContentText("Você venceu!");
        } else {
            winnerAlert.setHeaderText("Não foi dessa vez!");
            winnerAlert.setContentText(gameManager.getWinnerName() + " venceu o jogo!");
        }
        Platform.runLater(() -> {
            winnerAlert.showAndWait();
            Platform.exit();
        });
    }

    /**
     * Renderiza o nome do jogador e invoca o método de renderizar cartas.
     */
    private void renderPlayerInfo() {
        playerTextField.setText("Vez de: " + gameManager.getCurrentPlayerName());
        renderPlayerHand();
    }

    /**
     * Renderiza as cartas da mão do jogador.
     */
    private void renderPlayerHand() {
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

    /**
     * Captura o clique na carta e renderiza o box de ações.
     */
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

    /**
     * Captura o clique no botão de jogar e executa a ação de jogar carta.
     */
    protected void handlePlayBtnClick() {
        gameManager.playCard(uiManager.getClickedCard());
    }

    @FXML
    /**
     * Captura o clique no botão de visualizar carta e executa a animação para mostrar.
     */
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

    /**
     * Renderiza as mensagens do histórico de ações
     */
    protected void renderHistory() {
        historyTextArea.clear();
        for (var message : uiManager.getHistory()) {
            historyTextArea.appendText(message);
        }
    }

    // Métodos de animação

    private void animateCardDiscarded() {
        var cardNode = playerHandGridPane.getChildren().get(uiManager.getClickedCard());

        applyTransition(cardNode, Duration.millis(500), TransitionType.FADE_OUT, () -> {
            playerHandGridPane.getChildren().remove(cardNode);
            renderPlayerHand();
            renderDiscardPile();
        });

    }

    /**
     * Renderiza a carta do topo da pilha de descarte.
     */
    private void renderDiscardPile() {
        if (discardPileImage != null)
            pilesPane.getChildren().remove(discardPileImage);

        Image cardImage = CardAssetMapper.getAsset(gameManager.getTopCardOnDiscardPile());

        ImageView discardImageView = new ImageView(cardImage);
        discardImageView.setFitWidth(uiManager.getCardWidth());
        discardImageView.setFitHeight(uiManager.getCardHeight());
        discardImageView.setLayoutX(uiManager.getDiscardPaneCoords().getX());
        discardImageView.setLayoutY(uiManager.getDiscardPaneCoords().getY());

        applyTransition(discardImageView, Duration.millis(500), TransitionType.FADE_IN);

        pilesPane.getChildren().add(discardImageView);
    }


    private void animateCardDrawn() {
        ImageView drawCardImage = new ImageView(drawPileImage.getImage());

        ((Pane) drawPileImage.getParent()).getChildren().add(drawCardImage);

        applyTransition(drawCardImage, Duration.millis(500), TransitionType.FADE_OUT, () -> {
            playerHandGridPane.getChildren().add(drawCardImage);
            renderPlayerHand();

            ((Pane) drawPileImage.getParent()).getChildren().remove(drawCardImage);
            applyTransition(drawCardImage, Duration.millis(500), TransitionType.FADE_IN);
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