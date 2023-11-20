package com.game.snap.model;

import lombok.Getter;

import java.util.Stack;

@Getter
public class PlayerDeck {
    private final Stack<Card> cards;
    private final Stack<Card> flippedCards;

    public PlayerDeck() {
        this.cards = new Stack<>();
        this.flippedCards = new Stack<>();
    }
}
