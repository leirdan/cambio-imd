package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;
import java.util.Stack;
import br.ufrn.imd.cambio_imd.models.cards.Card;

public class CutCommand implements ICommand {


    @Override
    public void execute() {
   /*     // Carregando objetos para avaliação
        GameContext context = GameContext.getInstance();
        Players players = context.getPlayers();
        Player currentPlayer = players.findById(context.getLastPlayerToPlayId()).orElse(null);
        DiscardPile pile = context.getDiscardPile();

        // Acessando a carta do topo e a carta abaixo da do topo
        Stack<Card> cards = pile.getCards();

        if (cards.size() < 2) {
            throw new IllegalStateException("Not enough cards in the pile!");
        }
        Card topCard = cards.peek(); //< Carta no topo da pilha
        Card secondCard = cards.get(cards.size() - 2); //< Carta no índice abaixo do topo

        // Verificando as possíveis punições ou não para o jogador:
        // Aqui estamos partindo do pressuposto que, ao chamar 
        if(!topCard.equals(secondCard)){
            if(currentPlayer.madeWrongCut()){
                currentPlayer.setProhibitedCut(true);
            } else {
                currentPlayer.setWrongCut(true);
            }
        }
    }

    */
    }
}

