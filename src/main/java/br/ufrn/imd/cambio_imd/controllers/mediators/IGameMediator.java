package br.ufrn.imd.cambio_imd.controllers.mediators;

import br.ufrn.imd.cambio_imd.commands.ExchangeCardCommand;

// Definir operações de jogo AQUI!!
public interface IGameMediator {
    // TODO: mudar nome disso aqui pra algo como "Jogador troca carta com Deck" sei la
    void exchangeCardsBetweenPlayerAndDeck(ExchangeCardCommand command);
}
