package br.ufrn.imd.cambio_imd.models.players;

import br.ufrn.imd.cambio_imd.enums.PlayerType;
import br.ufrn.imd.cambio_imd.exceptions.EmptyCardException;
import br.ufrn.imd.cambio_imd.models.Entity;
import br.ufrn.imd.cambio_imd.models.cards.Card;


/**
 * Classe que representa um jogador.
 * Possui informações sobre o jogador e suas ações.
 * Herda de @see Entity,
 * Possui atributos como:
 * @see CardHand,
 * @see PlayerType,
 * @see Card,
 * @see EmptyCardException
 */
public class Player extends Entity {
    /**
     * Variável que armazena o nome do jogador.
     */
    private String name = "";
    
    /**
     * Inicialização da mão do jogador.
     */
    private CardHand cardHand = new CardHand();
    
    /**
     * Variável que armazena o tipo do jogador.
     */
    private PlayerType type = PlayerType.ROBOT;

    /**
     * Armazena se o jogador já realizou o primeiro corte ou não.
     */
    private boolean wrongCut = false;

    /**
     * Indica se o jogador está proibido de cortar após um erro no corte inicial.
     */
    private boolean prohibitedCut = false;

    /**
     * Construtor padrão da classe Player.
     */
    public Player() {
        super();
    }

    /**
     * Construtor da classe Player com nome e tipo de jogador.
     *
     * @param name o nome do jogador.
     * @param type o tipo do jogador (HUMANO ou ROBÔ).
     */
    public Player(String name, PlayerType type) {
        super();
        this.name = name;
        this.type = type;
    }

    /**
     * Construtor da classe Player com múltiplos atributos.
     *
     * @param name o nome do jogador.
     * @param wrongCut indica se o jogador realizou um corte errado.
     * @param cardHand a mão de cartas do jogador.
     * @param type o tipo do jogador (HUMANO ou ROBÔ).
     */
    public Player(String name, boolean wrongCut, CardHand cardHand, PlayerType type) {
        super();
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
        this.type = type;
    }

    /**
     * Construtor da classe Player com todos os atributos incluindo o identificador.
     *
     * @param id o identificador único do jogador.
     * @param name o nome do jogador.
     * @param wrongCut indica se o jogador realizou um corte errado.
     * @param cardHand a mão de cartas do jogador.
     * @param type o tipo do jogador (HUMANO ou ROBÔ).
     */
    public Player(int id, String name, boolean wrongCut, CardHand cardHand, PlayerType type) {
        super(id);
        this.name = name;
        this.wrongCut = wrongCut;
        this.cardHand = cardHand;
        this.type = type;
    }

    /**
     * Obtém o nome do jogador.
     *
     * @return o nome do jogador.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do jogador.
     *
     * @param name o novo nome do jogador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Verifica se o jogador realizou um corte errado.
     *
     * @return true se o jogador realizou um corte errado, false caso contrário.
     */
    public boolean madeWrongCut() {
        return wrongCut;
    }

    /**
     * Obtém a mão de cartas do jogador.
     *
     * @return a mão de cartas do jogador.
     */
    public CardHand getHand() {
        return cardHand;
    }

    /**
     * Define a mão de cartas do jogador.
     *
     * @param cardHand a nova mão de cartas do jogador.
     */
    public void setHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }

    /**
     * Adiciona uma carta à mão do jogador.
     *
     * @param card a carta a ser adicionada.
     */
    public void addCard(Card card) {
        cardHand.addCard(card);
    }

    /**
     * Remove uma carta da mão do jogador pelo índice.
     *
     * @param index o índice da carta a ser removida.
     * @return a carta removida.
     */
    public Card removeCard(int index) {
        return cardHand.removeCard(index);
    }

    /**
     * Verifica se o jogador está proibido de cortar.
     *
     * @return true se o jogador está proibido de cortar, false caso contrário.
     */
    public boolean isProhibitedCut() {
        return prohibitedCut;
    }

    /**
     * Define se o jogador está proibido de cortar.
     *
     * @param prohibitedCut true para proibir o corte, false para permitir.
     */
    public void setProhibitedCut(boolean prohibitedCut) {
        this.prohibitedCut = prohibitedCut;
    }

    /**
     * Verifica se o jogador realizou um corte errado.
     *
     * @return true se o jogador realizou um corte errado, false caso contrário.
     */
    public boolean isWrongCut() {
        return wrongCut;
    }

    /**
     * Define se o jogador realizou um corte errado.
     *
     * @param wrongCut true se o jogador realizou um corte errado, false caso contrário.
     */
    public void setWrongCut(boolean wrongCut) {
        this.wrongCut = wrongCut;
    }

    /**
     * Verifica se o jogador é humano.
     *
     * @return true se o jogador é humano, false se é um robô.
     */
    public boolean isHuman() {
        return this.type == PlayerType.HUMAN;
    }
}
