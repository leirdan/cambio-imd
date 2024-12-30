package br.ufrn.imd.cambio_imd.models.cards;

import javafx.util.Pair;

import java.util.Objects;

public class Card {
    /**
     * Par formado pelo símbolo da carta e seu real valor.
     */
    private Pair<String, Integer> data;

    /**
     * Construtor padrão da classe <code>Card</code>
     * @param key Um símbolo aceitável do baralho de cartas.
     *            <p> Os símbolos são
     *            <code> {A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K }</code>
     *            </p>
     */
    public Card(String key) {
        int value = 0;
        switch (key) {
            case "A":
                value = 1;
                break;
            case "8":
                value = 0;
                break;
            case "J":
                value = 11;
                break;
            case "Q":
                value = 12;
                break;
            case "K":
                value = 13;
                break;
            default:
                value = Integer.parseInt(key);
                break;
        }
        this.data = new Pair<>(key, value);
    }

    public Integer getValue() {
        return this.data.getValue();
    }


    /**
     * @return
     */
    public boolean isSuper() {
        var key = data.getKey();
        return key.equals("J") || key.equals("Q") || key.equals("K");
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: melhorar a conversão
        var card = (Card) obj;

        return Objects.equals(card.getValue(), this.getValue());
    }
}
