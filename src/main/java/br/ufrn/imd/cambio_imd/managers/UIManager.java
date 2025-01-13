package br.ufrn.imd.cambio_imd.managers;

import javafx.geometry.Point2D;
import br.ufrn.imd.cambio_imd.dao.GameDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe que representa o estado da interface gráfica, contendo informações sobre a interface gráfica.
 * Concentra todos os dados necessários para que outras classes possam acessar e modificar o estado da interface gráfica.
 */
public class UIManager {
    private double cardWidth;
    private double cardHeight;
    private int clickedCard;
    private Point2D discardPaneCoords = new Point2D(175, 12);
    private int remainingHints = 0;
    private static UIManager instance;
    private ArrayList<String> history = new ArrayList<>();

    private GameDAO context = GameDAO.getInstance();

    private UIManager() {
    }

    // Métodos relacionados a dimensões de carta

    /**
     * Método para obter a largura da carta.
     * @return largura da carta.
     */
    public double getCardWidth() {
        return cardWidth;
    }

    /**
     * Método para definir a largura da carta.
     * @param cardWidth largura da carta.
     */
    public void setCardWidth(double cardWidth) {
        this.cardWidth = cardWidth;
    }

    /**
     * Método para obter a altura da carta.
     * @return altura da carta.
     */
    public double getCardHeight() {
        return cardHeight;
    }

    /**
     * Método para definir a altura da carta.
     * @param cardHeight altura da carta.
     */
    public void setCardHeight(double cardHeight) {
        this.cardHeight = cardHeight;
    }

    // Métodos relacionados a coordenadas do painel de descarte

    /**
     * Método para obter as coordenadas do painel de descarte.
     * @return coordenadas do painel de descarte.
     */
    public Point2D getDiscardPaneCoords() {
        return discardPaneCoords;
    }

    /**
     * Método para definir as coordenadas do painel de descarte.
     * @param x coordenada x.
     * @param y coordenada y.
     */
    public void setDiscardPaneCoords(double x, double y) {
        discardPaneCoords = new Point2D(x, y);
    }

    // Métodos relacionados ao estado da carta clicada

    /**
     * Método para obter a carta clicada.
     * @return carta clicada.
     */
    public int getClickedCard() {
        return clickedCard;
    }

    /**
     * Método para definir a carta clicada.
     * @param clickedCard carta clicada.
     */
    public void setClickedCard(int clickedCard) {
        this.clickedCard = clickedCard;
    }

    // Métodos relacionados a dicas restantes

    /**
     * Método para obter as dicas restantes.
     * @return dicas restantes.
     */
    public int getRemainingHints() {
        return remainingHints;
    }

    /**
     * Método para definir as dicas restantes.
     * @param remainingHints dicas restantes.
     */
    public void setRemainingHints(int remainingHints) {
        this.remainingHints = remainingHints;
    }

    // Método Singleton

    /**
     * Método para obter a instância do UIManager.
     * @return instância do UIManager.
     */
    public static UIManager getInstance() {
        if (instance == null)
            instance = new UIManager();

        return instance;
    }

    // Métodos relacionados ao histórico

    /**
     * Método para adicionar mensagem ao histórico.
     * @param msg mensagem a ser adicionada.
     */
    public void addMessageOnHistory(String msg) {
        String instant = getFormattedInstant();
        this.history.add("[" + instant + "]: " + msg + "\n");
    }

    /**
     * Método para obter o histórico.
     * @return histórico.
     */
    public ArrayList<String> getHistory() {
        return this.history;
    }

    // Métodos relacionados a contagem de cartas restantes

    /**
     * Método para obter texto de contagem de cartas restantes no baralho de compra.
     * @return texto de contagem de cartas restantes no baralho de compra.
     */
    public String getDrawPileCountText() {
        return "Restam " + context.getDrawPile().getCards().size() + " cartas.";
    }

    // Método para formatar instante

    /**
     * Método para obter instante formatado.
     * @return instante formatado.
     */
    public String getFormattedInstant() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = dt.format(formatter);
        return formattedTime;
    }
}
