package br.ufrn.imd.cambio_imd.utility;

import br.ufrn.imd.cambio_imd.CambioApplication;
import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.Deck;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CardAssetMapper {
    private CardAssetMapper() {
    }

    private static final String BASE_PATH = "/br/ufrn/imd/cambio_imd/views/assets/cards/";
    private static final Map<String, Image> ASSET_MAP = initAssets();
    private static final Image BACK_ASSET = new Image(CambioApplication.class.getResource(BASE_PATH + "redBack.png").toExternalForm());

    private static Map<String, Image> initAssets() {
        try {
            var map = new HashMap<String, Image>();

            for (String suit : Deck.SUITS) {
                for (String rank : Deck.RANKS) {
                    String key = rank + " - " + suit;
                    String fileName = suit.toLowerCase() + rank + ".png";
                    map.put(key, new Image(CambioApplication.class.getResource(BASE_PATH + fileName).toExternalForm()));
                }
            }

            return map;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar imagens: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public static Image getAsset(Card card) {
        return ASSET_MAP.get(card.getKey() + " - " + card.getSuit());
    }

    public static Image getBackCardAsset() {
        return BACK_ASSET;
    }
}
