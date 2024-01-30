package GameLogic;

import GameLogic.AI.Player;
import GameLogic.AI.SimpleAI;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static GameLogic.DotAndBoxesGame.getTurn;

/**
 * TUI for the Dot and Boxes game.
 */
public class DotAndBoxesTUI {
    private static int gridSize;
    public static String player1Name;
    public static String player2Name;
    private static Player player2;
    /**
     * The main method that runs the Dot and Boxes TUI game.
     *
     * @param args
     */
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        try {
            System.out.println("Enter player 1 name: ");
            player1Name = reader.next();
            System.out.println("Enter player 2 name: ");
            player2Name = reader.next();

            DotAndBoxesGame game;
            SimpleAI simpleAI = new SimpleAI(player1Name, player2Name, gridSize, player2);

            if (player1Name.equals(simpleAI.getName()) ||player2Name.equals(simpleAI.getName())){
                game = new DotAndBoxesGame(player1Name, player2Name, gridSize, simpleAI);
            }else{
                game = new DotAndBoxesGame(player1Name, player2Name, gridSize, null);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid names.");
        }

        while (true) {
            try {
                System.out.println("What dimensions for the board? ");
                gridSize = reader.nextInt();
                if (gridSize <= 0) {
                    System.out.println("Please enter a positive integer for the board dimensions.");
                    continue;
                }
                break;
            }catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a valid integer for the board dimensions.");
                reader.nextLine();
            }
        }

        DotAndBoxesGame game = new DotAndBoxesGame(player1Name, player2Name, gridSize, null);

        String currentPlayer = player1Name;

        while (true) {
            int moveIdx;

            if(currentPlayer.equals("-AI")){
                SimpleAI simpleAI = new SimpleAI(player1Name, player2Name, gridSize, player2);
                moveIdx = (simpleAI.makeMove());
                System.out.println("AI's move: " + moveIdx);
            }else {
                System.out.println(currentPlayer + "'s turn: New move: ");
                System.out.println("Enter move index number (0 to " + (game.getNumEdges() - 1) + "): ");
                moveIdx = reader.nextInt();
                if (moveIdx > (game.getNumEdges() - 1) || moveIdx < 0) {
                    System.out.println("Wrong move index, please enter a valid move: ");
                    continue;
                }
            }


            int result = game.makeMove(currentPlayer, moveIdx);

            // Print status of board
            game.displayBoard();

            if (result == 2) {
                System.out.println("Game over");
                reader.close();
                //break to exit the while loop
                break;
            }
            // Alternate player here
            currentPlayer = getTurn(currentPlayer, result);

        }
    }
}