package com.game.snap.model;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private int order;
    private final PlayerDeck deck;

    public Player(String name, int order) {
        this.deck = new PlayerDeck();
        this.name = name;
        this.order = order;
    }
}
