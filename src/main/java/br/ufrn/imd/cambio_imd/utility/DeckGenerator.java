package br.ufrn.imd.cambio_imd.utility;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.Deck;

import java.util.Stack;

/**
 *
 */
public class DeckGenerator {
    private DeckGenerator() {}
    public static Stack<Card> generate() {
        Stack<Card> deck = new Stack<>();
        for (String suit : Deck.SUITS) {
            for (String rank : Deck.RANKS) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }
}
