package com.game.snap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

// I created this class just for console display reasons
// If not for console display I wouldn't use this class
@Getter
@AllArgsConstructor
public class PlayerFlippedCardPair {
    Player currentPlayer;
    Card flippedCard;
}