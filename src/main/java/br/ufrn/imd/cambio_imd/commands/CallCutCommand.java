package br.ufrn.imd.cambio_imd.commands;

import br.ufrn.imd.cambio_imd.dao.GameContext;
import br.ufrn.imd.cambio_imd.models.cards.DiscardPile;
import br.ufrn.imd.cambio_imd.models.players.Player;
import br.ufrn.imd.cambio_imd.models.players.Players;
import java.util.Iterator;

public class CallCutCommand implements ICommand {
    private int playerId;

    public CallCutCommand(int playerId){
        this.playerId = playerId;
    }

    @Override
    public void execute(){
        // Aqui estamos apenas pegando o jogador para fazer sua incrível jogada e o deixar disponível para o corte.
        GameContext context = GameContext.getInstance(); //< Pegando o contexto do jogo
        
        DiscardPile pile = context.getDiscardPile(); //< Pegando a pilha de descarte
        Players players = context.getPlayers(); //< Pegando os jogadores (estranho...)
        Player currentPlayer = players.findById(playerId).orElseThrow(() -> new IllegalArgumentException("Player not found")); //< Pegando o jogador 

        if(currentPlayer.isProhibitedCut()){
            System.out.println("Jogador não pode cortar!");
            // TODO: Ações para continuar o corte sem o jogador
        } else {
            context.setCurrentPlayerToCut(currentPlayer); //< Setando o jogador atual para cortar
        }
    }
    

}
