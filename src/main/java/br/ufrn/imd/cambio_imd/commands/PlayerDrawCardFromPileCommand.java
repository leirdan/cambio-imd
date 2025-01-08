package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class PlayerDrawCardFromPileCommand implements ICommand {
    private Player player;
    private DrawPile pile;
    private int cardIndex;

    public PlayerDrawCardFromPileCommand(Player player, DrawPile pile, int cardIndex) {
        this.player = player;
        this.pile = pile;
        this.cardIndex = cardIndex;
    }

    @Override
    public void execute() {
        // Jogador descarta uma carta
        Card discardedCard = player.removeCard(cardIndex);

        // Remove a primeira carta da pilha de descarte
        Card newCard = pile.removeTopCard();

        // Adiciona a carta descartada na pilha e randomiza
        pile.addCard(discardedCard);
        pile.shuffle();

        // Adiciona a carta da pilha na mão do jogador
        player.addCard(newCard);
    }
}
