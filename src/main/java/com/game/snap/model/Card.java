package com.game.snap.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Card {
    private int value;
    private Suit suit;

    @Override
    public String toString() {
        return String.format("%s [%s]", value, suit.name());
    }
}
