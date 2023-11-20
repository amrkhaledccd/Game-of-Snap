package com.game.snap.manager;

import com.game.snap.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class GameManager implements Game {
    private static GameManager instance;
    public Queue<Player> players;
    public Stack<Card> deck;
    private Matching matching;
    private final PlayerDeckManager playerDeckManager;

    private GameManager() {
        matching = Matching.VALUE;
        playerDeckManager = new PlayerDeckManager();
    }
    public static GameManager getInstance() {
        if(instance == null) {
           instance = new GameManager();
        }
        return instance;
    }

    @Override
    public void initGame(int numOfPlayers, Matching matching) {
        this.matching = matching;
        generateDeck();
        shuffle();
        createPlayers(numOfPlayers);
        dealCards();
    }

    @Override
    public boolean isGameEnd() {
        return players.size() == 1;
    }

    @Override
    public Optional<PlayerFlippedCardPair> nextPlay() {
        var currentPlayer = players.remove();
        var flippedCard = playerDeckManager.flipCard(currentPlayer.getDeck());

        // if no card is returned, the player has no cards and should be removed from the game
        if(flippedCard.isPresent()) {
            // add the player to the end of the queue
            players.add(currentPlayer);
            return Optional.of(new PlayerFlippedCardPair(currentPlayer, flippedCard.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Player> shoutSnap(Card flippedCard, Player currentPlayer) {
        var decks = players.stream()
                .filter(player -> player.getOrder() != currentPlayer.getOrder())
                .map(Player::getDeck)
                .toList();

        var matchedDeck = playerDeckManager.findMatchedDeck(matching, flippedCard, decks);

        // if card matched, randomize a player to snap
        // I assume that there always be a player to snap.
        if (matchedDeck.isPresent()) {
            // randomize a player to snap
            var snappedPlayer = getRandomPlayer();
            // add both decks to the snapped player deck
            playerDeckManager
                    .mergeDecks(snappedPlayer.getDeck(), currentPlayer.getDeck(), matchedDeck.get());

            return Optional.of(snappedPlayer);
        }
        return Optional.empty();
    }

    //returns the top flipped cards for each player, used only for console output
    @Override
    public String getGameStates() {
        var playerList = players.stream().sorted(Comparator.comparingInt(Player::getOrder)).toList();
        return playerList.stream().map(player -> {
            if(player.getDeck().getFlippedCards().isEmpty()) {
                return player.getName() + ": []";
            }
            return String.format("%s: %s", player.getName(), player.getDeck().getFlippedCards().peek());
        }).collect(Collectors.joining("  "));
    }

    @Override
    public Player getWinner() {
        return players.peek();
    }

    private void generateDeck() {
        deck = new Stack<>();
        for(int i = 0; i < Suit.values().length; i++) {
            var suit = Suit.values()[i];
            for (int j = 1; j <= 13; j++) {
                deck.push(new Card(j, suit));
            }
        }
    }

    private void shuffle() {
        Collections.shuffle(deck);
    }

    private void createPlayers(int numOfPlayers) {
        players = new LinkedList<>();
        for(int i = 0; i < numOfPlayers; i++) {
            players.add(new Player("P" + (i + 1), i));
        }
    }

    private void dealCards() {
        while(!deck.isEmpty()) {
            var currentPlayer = players.remove();
            playerDeckManager.addCard(currentPlayer.getDeck(), deck.pop());
            players.add(currentPlayer);
        }

        // move first player to the top of the queue
        while(!players.isEmpty() && players.peek().getOrder() != 0) {
            players.add(players.remove());
        }
    }

    private Player getRandomPlayer() {
        var random = new Random();
        var playerList = players.stream().toList();
        int randomIdx = random.nextInt(playerList.size() - 1) + 1;
        return playerList.get(randomIdx);
    }
}