package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameDAO;

import java.util.Collections;

public class TransferDiscardCardsToDrawPileCommand  implements  ICommand{
    @Override
    public void execute() {
        GameDAO context = GameDAO.getInstance();

        var discardPile = context.getDiscardPile();
        var drawPile = context.getDrawPile();

        // Pega a primeira carta da pilha de descarte
        var topCard = discardPile.getCards().pop();

        // Distribui as cartas pra drawPile
        for (var card : discardPile.getCards()) {
            drawPile.addCard(card);
        }
        // Embaralha as cartas da drawPile
        Collections.shuffle(drawPile.getCards());

        // Remove todas as cartas anteriores e adiciona a que estava em cima
        discardPile.getCards().clear();
        discardPile.addCard(topCard);
    }
}
