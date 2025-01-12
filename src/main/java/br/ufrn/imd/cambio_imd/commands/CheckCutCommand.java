package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.Card;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

import java.util.Stack;

public class CheckCutCommand implements ICommand {
    private Player player;
    private DiscardPile discardPile;

    public CheckCutCommand(Player player, DiscardPile pile) {
        this.player = player;
        this.discardPile = pile;
    }

    @Override
    public void execute() {
        Stack<Card> cards = discardPile.getCards();

        if (cards.size() < 2) {
            throw new IllegalStateException("Sem cartas suficientes na pilha para verificar corte!");
        }

        Card topCard = cards.peek(); //< Carta no topo da pilha
        Card secondCard = cards.get(cards.size() - 2); //< Carta no índice abaixo do topo

        boolean madeWrongCut = !topCard.equals(secondCard);

        // Se o jogador já fez um corte errado anteriormente, ele está proibido de cortar.
        // Se é a primeira vez do corte errado, ele só é avisado.
        if (madeWrongCut) {
            if (player.madeWrongCut()) {
                player.setProhibitedCut(true);
            } else {
                player.setWrongCut(true);
            }
        }
    }
}

