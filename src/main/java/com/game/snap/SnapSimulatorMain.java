package com.game.snap;

import com.game.snap.manager.GameManager;
import com.game.snap.model.Matching;

import java.util.Scanner;

public class SnapSimulatorMain {
    public static void main(String[] args) {
        int numOfPlayers;
        int matching;
        do {
            Scanner scanner = new Scanner( System.in);
            System.out.print("Enter number of Players (2 - 8): ");
            numOfPlayers = scanner.nextInt();
        } while(numOfPlayers < 2 || numOfPlayers > 8);

        do {
            Scanner scanner = new Scanner( System.in);
            System.out.print("Choose card matching (1-Value  2-Suit  3-Both): ");
            matching = scanner.nextInt();
        } while(matching < 1 || matching > 3);

        System.out.println("==================================");
        System.out.println("========== Game Of Snap ==========");
        System.out.println("==================================");

        var game = GameManager.getInstance();
        game.initGame(numOfPlayers, Matching.values()[matching - 1]);

        while (!game.isGameEnd()) {
            var playerFlippedCard = game.nextPlay();
            System.out.println(game.getGameStates());
            playerFlippedCard.ifPresent(playerCard -> {
                var player = game.shoutSnap(playerCard.getFlippedCard(), playerCard.getCurrentPlayer());
                player.ifPresent(p -> System.out.printf("Player %s shouted snap \n", p.getName()));
            });
        }

        System.out.println("================= Winner ===============");
        System.out.println("Congratulations " + game.getWinner().getName());
        System.out.println("========================================");
    }
}