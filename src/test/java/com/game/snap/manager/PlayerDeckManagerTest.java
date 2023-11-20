package com.game.snap.manager;

import com.game.snap.model.Card;
import com.game.snap.model.Matching;
import com.game.snap.model.PlayerDeck;
import com.game.snap.model.Suit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Because of time constraint I wrote a few tests and test all methods
// but it shows the idea
public class PlayerDeckManagerTest {

    PlayerDeckManager playerDeckManager;

    @BeforeEach
    public void beforeAll() {
        playerDeckManager = new PlayerDeckManager();
    }
    @Test
    public void testFlipCard_whenCardsNotEmpty_returnCardOnTop() {
        var playerDeck = new PlayerDeck();
        var expectedCard = new Card(10, Suit.HEART);
        playerDeck.getCards().push(new Card(2, Suit.DIAMOND));
        playerDeck.getCards().push(expectedCard);
        var result = playerDeckManager.flipCard(playerDeck);
        assertTrue(result.isPresent());
        assertEquals(expectedCard, result.get());
        assertFalse(playerDeck.getFlippedCards().isEmpty());
        assertEquals(expectedCard, playerDeck.getFlippedCards().peek());
    }

    @Test
    public void testFlipCard_whenCardsAndFlippedCardsNotEmpty_returnFlippedCardsBottomCard() {
        var playerDeck = new PlayerDeck();
        var expectedCard = new Card(10, Suit.HEART);
        playerDeck.getFlippedCards().push(expectedCard);
        playerDeck.getFlippedCards().push(new Card(2, Suit.DIAMOND));
        var result = playerDeckManager.flipCard(playerDeck);
        assertTrue(result.isPresent());
        assertEquals(expectedCard, result.get());
        assertFalse(playerDeck.getFlippedCards().isEmpty());
        assertEquals(expectedCard, playerDeck.getFlippedCards().peek());
    }

    @Test
    public void testFlipCard_whenCardsEmpty_returnEmptyOptional() {
        var playerDeck = new PlayerDeck();
        var result = playerDeckManager.flipCard(playerDeck);
        assertTrue(result.isEmpty());
        assertTrue(playerDeck.getFlippedCards().isEmpty());
    }

    @Test
    public void testFindMatchedDeck_givenCard_whenDeckMatched_returnMatchedDeck() {
        var playerDecks = getPlayerDeckWithRandomFlippedCards();
        var matchCard = new Card(3, Suit.HEART);
        var expectedDeck = playerDecks.get(1);
        expectedDeck.getFlippedCards().push(matchCard);
        var result = playerDeckManager.findMatchedDeck(Matching.VALUE, matchCard, playerDecks);
        assertTrue(result.isPresent());
        assertEquals(expectedDeck, result.get());
    }

    @Test
    public void testFindMatchedDeck_givenCard_whenNoMatchedDeck_returnEmptyOptional() {
        var playerDecks = getPlayerDeckWithRandomFlippedCards();
        var matchCard = new Card(3, Suit.HEART);
        var result = playerDeckManager.findMatchedDeck(Matching.VALUE, matchCard, playerDecks);
        assertTrue(result.isEmpty());
    }

    private List<PlayerDeck> getPlayerDeckWithRandomFlippedCards() {
        var random = new Random();
        List<PlayerDeck> decks = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            PlayerDeck deck = new PlayerDeck();
            for(int j = 0; j < 3; j++) {
                // generate number from 4 to 13
                int randomValue = random.nextInt(9) + 4;
                int randomSuitIdx = random.nextInt(3);
                var randomSuit = Suit.values()[randomSuitIdx];
                deck.getFlippedCards().push(new Card(randomValue, randomSuit));
            }
            decks.add(deck);
        }
        return decks;
    }
}
