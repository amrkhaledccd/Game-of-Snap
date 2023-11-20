package com.game.snap.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Card {
    private int value;
    private Suit suit;

    @Override
    public String toString() {
        var valueStr = switch (value) {
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(value);
        };
        return String.format("%s [%s]", valueStr, suit.name());
    }
}
