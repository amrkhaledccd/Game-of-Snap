# Game-of-Snap
# Notes
1- For time constraints I wrote a few tests to show the idea.

2- I didn't understand the requirement "ask user how many playing card decks to play with"
I assumed it is the same as number of players (since each player has a deck to play with).

3- The game allow from 2 to 8 players.

# Highlights
 **PlayerDeck** -> is a player stacks of cards, contains two stacks, flipped and non flipped cards.

**PlayDeckManager** -> is responsible to manage playerDeck, it flip the card for the current player
and check if there is matched card in the top of other players stack based on the choosed Matching.

**GameManager** it maintain a queue of players and is responsible for constructing game cards, shuffle them and deal the cards to the players.
these methods are straight forward.

The most important methods are nextPlay() and shoutSnap()

**nextPlay()** asks playerDeckManager to flip the card for the current player, if the player doesn't have any cards,
the player is removed from the game.

**shoutSnap** asks playerDeckManager to match the flipped card with other players' flipped deck
if matched, it generate a random play to shout snap.

The game ends when there is only one player left in the queue.
 