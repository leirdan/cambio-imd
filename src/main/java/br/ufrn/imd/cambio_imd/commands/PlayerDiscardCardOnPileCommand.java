package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class PlayerDiscardCardOnPileCommand implements ICommand {
    private Player player;
    private DiscardPile pile;
    private int cardIndex;

    public PlayerDiscardCardOnPileCommand(Player player, DiscardPile pile, int cardIndex) {
        this.player = player;
        this.pile = pile;
        this.cardIndex = cardIndex;
    }

    @Override
    public void execute(){
       // Pegar carta selecionada
        Card selectedCard = player.removeCard(cardIndex);

        // Inserir na pilha de descarte
        pile.addCard(selectedCard);
    }
}
