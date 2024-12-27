package br.ufrn.imd.cambio_imd.models.cards;

import javafx.util.Pair;

import java.util.Objects;


public class Card {
    private Pair<String, Integer> data;

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
