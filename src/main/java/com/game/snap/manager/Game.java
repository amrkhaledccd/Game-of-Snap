package com.game.snap.manager;

import com.game.snap.model.Card;
import com.game.snap.model.Matching;
import com.game.snap.model.Player;
import com.game.snap.model.PlayerFlippedCardPair;

import java.util.Optional;

public interface Game {
    void initGame(int numOfPlayers, Matching matching);
    Optional<PlayerFlippedCardPair> nextPlay();
    Optional<Player> shoutSnap(Card flippedCard, Player currentPlayer);
    boolean isGameEnd();
    String getGameStates();
    Player getWinner();
}
