package com.game.snap.manager;

import com.game.snap.model.Card;
import com.game.snap.model.Matching;
import com.game.snap.model.PlayerDeck;
import java.util.List;
import java.util.Optional;

public class PlayerDeckManager {
    public void addCard(PlayerDeck deck, Card card) {
        deck.getCards().push(card);
    }

    // Takes a card form cards stack and added to the flipped card stack
    // if cards stack is empty, it flips the flipped cards (add flipped cards to cards stack)
    public Optional<Card> flipCard(PlayerDeck deck) {
        if(deck.getCards().isEmpty()) {
            while(!deck.getFlippedCards().isEmpty()) {
                deck.getCards().push(deck.getFlippedCards().pop());
            }
        }

        if(!deck.getCards().isEmpty()) {
           deck.getFlippedCards().push(deck.getCards().pop());
        }

        return deck.getFlippedCards().isEmpty() ?
                Optional.empty() :
                Optional.of(deck.getFlippedCards().peek());
    }

    // Check the top of the stack of all flipped cards and return the deck that matched
    public Optional<PlayerDeck> findMatchedDeck(Matching matching, Card card, List<PlayerDeck> decks) {
        for(PlayerDeck deck: decks) {
            if(!deck.getFlippedCards().isEmpty()) {
                var tosCard = deck.getFlippedCards().peek();
                if(isMatching(matching, card, tosCard)) {
                    return Optional.of(deck);
                }
            }
        }
        return Optional.empty();
    }

    //add flippedCards of deck1 and deck2 to snapped player deck
    public void mergeDecks(PlayerDeck snappedDeck, PlayerDeck deck1, PlayerDeck deck2) {
        snappedDeck.getCards().addAll(deck1.getFlippedCards());
        snappedDeck.getCards().addAll(deck2.getFlippedCards());
        deck1.getFlippedCards().removeAllElements();
        deck2.getFlippedCards().removeAllElements();
    }

    private boolean isMatching(Matching matching, Card card1, Card card2) {
        return switch(matching) {
            case VALUE -> card1.getValue() == card2.getValue();
            case SUIT -> card1.getSuit() == card2.getSuit();
            case BOTH -> card1.getValue() == card2.getValue() || card1.getSuit() == card2.getSuit();
        };
    }
}
