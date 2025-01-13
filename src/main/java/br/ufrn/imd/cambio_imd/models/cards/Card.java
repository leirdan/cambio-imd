package br.ufrn.imd.cambio_imd.models.cards;

import javafx.util.Pair;

/**
 * Classe que representa uma carta de baralho.
 * É utilizada em @see Deck e em suas classes filhas.
 */
public class Card {
    /**
     * Naipe da carta.
     */
    private String suit;
    /**
     * Par formado pelo número da carta e seu real valor.
     */
    private Pair<String, Integer> data;

    /**
     * Construtor padrão da classe <code>Card</code>
     *
     * @param key Um símbolo aceitável do baralho de cartas.
     *            <p> Os símbolos são
     *            <code> {Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King }</code>
     *            </p>
     * @param suit O naipe da carta.
     *            <p> Os naipes são
     *           <code> {Heart, Diamond, Club, Spade} </code>
     *          </p>
     */
    public Card(String suit, String key) {
        this.suit = suit;

        int value = 0;
        switch (key) {
            case "Ace":
                value = 1;
                break;
            case "8":
                value = 0;
                break;
            case "Jack":
                value = 11;
                break;
            case "Queen":
                value = 12;
                break;
            case "King":
                value = 13;
                break;
            default:
                value = Integer.parseInt(key);
                break;
        }
        this.data = new Pair<>(key, value);
    }

    /**
     * Obtém o naipe da carta.
     * @return o naipe da carta.
     */
    public String getSuit() {
        return this.suit;
    }

    /**
     * Obtém o tipo da carta, independente de numeração.
     * @return o tipo da carta, independente de numeração.
     */
    public String getKey() {
        return this.data.getKey();
    }

    /**
     * Obtém o valor da carta.
     * @return o valor da carta.
     */
    public Integer getValue() {
        return this.data.getValue();
    }

    /**
     * Retorna se a carta é uma super carta ou, em outras palavras, uma carta de ação.
     * @return se a carta é uma super carta. True para sim, false para cartas normais.
     */
    public boolean isSuper() {
        var key = data.getKey();
        return key.equals("Jack") || key.equals("Queen") || key.equals("King");

    }

    /**
     * Compara duas cartas para ver se são iguais.
     * @param obj Carta a ser comparada.
     * @return booleano para ver se a carta consegue 
     */
    @Override
    public boolean equals(Object obj) {
        try {
            if (obj.getClass() != this.getClass())
                return false;

            var card = (Card) obj;

            return card.getValue().equals(this.getValue()) &&
                    card.getKey().equals(this.getKey());
        } catch (NullPointerException ex) {
            System.out.println("Elemento passado não é um card.");
            return false;
        }
    }

    /**
     * Retorna o tipo da carta em forma de string com o naipe e tipo.
     * @return a representação da carta em string.
     */
    @Override
    public String toString() {
        return getKey() + " de " + getSuit();
    }
}
