package br.ufrn.imd.cambio_imd.utility;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.Deck;

import java.util.HashMap;
import java.util.Map;

public class CardAssetMapper {
    private static final String BASE_PATH = "br/ufrn/imd/cambio_imd/views/assets/cards/";
    private static final Map<String, String> ASSET_MAP = initAssets();

    private static Map<String, String> initAssets() {
        var map = new HashMap<String, String>();

        for (String suit : Deck.SUITS) {
            for (String rank : Deck.RANKS) {
                String key = rank + " of " + suit;
                String fileName = suit.toLowerCase() + rank + ".png";
                map.put(key, BASE_PATH + fileName);
            }
        }

        return map;
    }

    public static String getAssetPath(Card card) {
        return ASSET_MAP.get(card.getKey() + " - " + card.getSuit());
    }
}
