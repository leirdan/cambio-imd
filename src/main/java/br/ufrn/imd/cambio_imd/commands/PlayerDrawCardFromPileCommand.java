package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class PlayerDrawCardFromPileCommand implements ICommand {
    private Player player;
    private DrawPile pile;

    public PlayerDrawCardFromPileCommand(Player player, DrawPile pile) {
        this.player = player;
        this.pile = pile;
    }

    @Override
    public void execute() {
        // Remove a primeira carta da pilha de descarte
        Card newCard = pile.removeTopCard();

        // Adiciona a carta da pilha na mão do jogador
        player.addCard(newCard);
    }
}
