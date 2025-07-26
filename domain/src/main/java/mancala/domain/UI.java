package mancala.domain;
import java.util.Scanner;

public class UI {

    public UI() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NormalPit pit = new NormalPit();
        int input;
        GameTracker gameTracker = new GameTracker();
        //uncomment to load game
        boolean continueGame = true;
        if (continueGame) {
            String gameState = "3_";
            pit = gameTracker.playGameState(gameState, pit);
        }
        for (int i = 14; i > 6; i--) {
            System.out.print(pit.findNeighborFromPitNr(i).getSeedCount()+" | ");
            if (i == 7) {
                System.out.println("");
                System.out.println("------------------");
            }
        }
        System.out.print("   ");
        for (int i = 1; i <= 6; i++) {
            System.out.print(pit.findNeighborFromPitNr(i).getSeedCount()+" | ");
        }
        while (true) {
            System.out.println(" ");
            System.out.println(gameTracker.returnGameState());
            System.out.println(" ");
            if (pit.checkForWinner()) {
                Player winner = pit.returnWinner();
                System.out.println("player"+winner+" has won!");
                break;
            }
            if (pit.findNeighborFromPitNr(1).getOwner().isTurn()) {
                System.out.println("give input move 1-6");
                input = scanner.nextInt();
            } else {
                System.out.println("give input move 8-13");
                input = scanner.nextInt();
            }
            ((NormalPit) pit.findNeighborFromPitNr(input)).doMove();
            gameTracker.addMoveToGameState(input);
            for (int i = 14; i > 6; i--) {
                System.out.print(pit.findNeighborFromPitNr(i).getSeedCount()+" | ");
                if (i == 7) {
                    System.out.println("");
                    System.out.println("------------------");
                }
            }
            System.out.print("   ");
            for (int i = 1; i <= 6; i++) {
                System.out.print(pit.findNeighborFromPitNr(i).getSeedCount()+" | ");
                }
            }
        }
}
