package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.models.cards.DrawPile;
import br.ufrn.imd.cambio_imd.models.players.Player;

public class ExchangeCardCommand implements ICommand {
    private Player player;
    private DrawPile drawPile;
    private int index;

    public ExchangeCardCommand(Player player, DrawPile drawPile, int index) {
        this.player = player;
        this.drawPile = drawPile;
        this.index = index;
    }

    @Override
    // TODO: implementar direito
    public void execute() {
        /*
        // Pegar carta de jogador
        Card card = player.drawCard(index);
        // Pegar e remover primeira carta do baralho de reposição
        Card newCard = drawPile.getTopCard();
        // Inserir carta do jogador na reposição e embaralhar
        drawPile.insertCard(card);
        drawPile.shuffle();
        // Retornar carta da reposição
        player.receberCarta(newCard, index);
        */
    }
}
