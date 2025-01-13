package br.ufrn.imd.cambio_imd.models.cards;

import br.ufrn.imd.cambio_imd.exceptions.EmptyCardException;
import br.ufrn.imd.cambio_imd.exceptions.EmptyDeckException;

import java.util.Stack;

/**
 * Classe que representa um baralho genérico.
 * Serve como base para as classes @see DiscardPile, @see DrawPile e @see CardHand.
 * Armazena objetos do tipo @see Card.
 * Não é usada diretamente, tem uso herdado.
 */
public abstract class Deck {
    /**
     * Array de naipes para determinar as cartas.
     */
    public static final String[] SUITS = {"Heart", "Diamond", "Club", "Spade"};
    /**
     * Array de valores para determinar as cartas.
     */
    public static final String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    /**
     * Inicialização da pilha de cartas.
     */
    protected Stack<Card> cards = new Stack<>();

    /**
     * Obtém as cartas do baralho.
     * 
     * @return as cartas do baralho.
     */
    public Stack<Card> getCards() {
        return cards;
    }

    /**
     * Define as cartas do baralho.
     * @param cards
     */
    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    /**
     * Obtém a quantidade de cartas no baralho.
     * @return o número de cartas presentes no baralho.
     */
    public int getAmount() {
        return cards.size();
    }

    /**
     * Verifica se o baralho está vazio.
     * @return um booleano que indica se o baralho está vazio.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Adiciona uma carta ao baralho.
     * @param card a carta a ser adicionada.
     * @throws EmptyCardException se a carta for nula.
     */
    public void addCard(Card card) throws EmptyCardException {
        if (card == null) throw new EmptyCardException();

        cards.push(card);
    }

    /**
     * Remove uma carta do baralho pelo índice.
     * @param cardIndex o índice da carta a ser removida.
     * @return a carta removida.
     * @throws ArrayIndexOutOfBoundsException se o índice for inválido.
     * @throws EmptyDeckException se o baralho estiver vazio.
     */
    public Card removeCard(int cardIndex) throws
            ArrayIndexOutOfBoundsException, EmptyDeckException {
        if (this.isEmpty())
            throw new EmptyDeckException();

        else if (cardIndex >= cards.size() || cardIndex < 0)
            throw new ArrayIndexOutOfBoundsException("Invalid index!");

        return cards.remove(cardIndex);
    }

}
