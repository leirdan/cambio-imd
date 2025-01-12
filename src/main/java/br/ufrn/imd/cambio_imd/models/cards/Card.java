package br.ufrn.imd.cambio_imd.models.cards;

import javafx.util.Pair;

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

    public String getSuit() {
        return this.suit;
    }

    public String getKey() {
        return this.data.getKey();
    }

    public Integer getValue() {
        return this.data.getValue();
    }


    /**
     * @return
     */
    public boolean isSuper() {
        var key = data.getKey();
        return key.equals("Jack") || key.equals("Queen") || key.equals("King");

    }

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

    @Override
    public String toString() {
        return getKey() + " de " + getSuit();
    }
}
