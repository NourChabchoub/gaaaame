package GameLogic;

import GameLogic.AI.Player;
import GameLogic.AI.SimpleAI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.Set;

import static GameLogic.DotAndBoxesTUI.player1Name;
import static GameLogic.DotAndBoxesTUI.player2Name;

/**
 * Represents a Dot and Boxes game.
 */
public class DotAndBoxesGame {
    private String name1;
    private String name2;
    private Player player2;
    private SimpleAI simpleAI;
    List<Integer> movesPlayer1 = new ArrayList<>();
    List<Integer> movesPlayer2 = new ArrayList<>();

    private String currentPlayer;

    private static Board board;

    private int gridSize;

    /**
     * Initializes a new DotAndBoxesGame with two player names.
     *
     * @param name1 The name of the first player.
     * @param name2 The name of the second player.
     */
    public DotAndBoxesGame(String name1, String name2, int gridSize, Player player2) {
        this.name1 = name1;
        this.name2 = name2;
        this.gridSize = gridSize;
        this.player2 = player2;

        currentPlayer = name1;

        this.board = new Board(gridSize, name1, name2);

        this.board.setupGrid();

    }

    /**
     * Makes a move in the game.
     *
     * @param currentPlayer The name of the current player.
     * @param moveIdx       The index of the move.
     * @return 0 if the move is valid, non-box-filling move;
     * 1 if the move filled a box;
     * 2 if the move finished the game (all boxes are filled);
     * 3 if the move is not valid (edge already filled).
     */
    public int makeMove(String currentPlayer, int moveIdx) {
        //Check if the edge had already been filled
        if (this.board.edgesArray[moveIdx]) {
            System.out.println("Move not registered, this edge has already been filled ");
            return 3;
        }

        int preMoveBoxCount = this.board.getNumberFilledBoxes();

        //Else mark the move
        this.board.edgesArray[moveIdx] = true;
        // Register current player move
        if (currentPlayer.equals(this.name1)) {
            this.movesPlayer1.add(moveIdx);
        } else {
            this.movesPlayer2.add(moveIdx);
        }

        int postMoveBoxCount = this.board.getNumberFilledBoxes();

        //If the number of boxes has changed since this move then a player filled a box
        if (postMoveBoxCount != preMoveBoxCount) {
            if (currentPlayer.equals(this.name1)) {
                System.out.println("New box filled in by " + currentPlayer);
                this.board.playerOneBoxes++;
                System.out.println(this.name1 + " box count: " + this.board.playerOneBoxes);
            } else {
                System.out.println("New box filled in by " + currentPlayer);
                this.board.playerTwoBoxes++;
                System.out.println(this.name2 + " box count: " + this.board.playerTwoBoxes);
            }
            System.out.println("Total filled-in box count: " + postMoveBoxCount);

            //Check if the game is over after this move
            if (postMoveBoxCount == gridSize * gridSize) {
                if (this.board.playerTwoBoxes > this.board.playerOneBoxes) {
                    System.out.println("Game over, " + this.name2 + " won!");
                } else {
                    System.out.println("Game over, " + this.name1 + " won!");
                }
                return 2;
            } else {
                return 1;
                //Need to find a row index and column index for box
            }
            //Else return 0, to indicate that the move was valid
        } else {
            return 0;
        }
    }


    public void displayBoard() {
        int i = 0;
        StringBuilder printBoard = new StringBuilder();
        //Actual value of the row index
        int actualRowIdx = 0;
        //Goes through every row
        for (int rowIdx = 0; rowIdx < ((gridSize * 4) + 1); rowIdx++) {
            //Goes through every column
            for (int columnIdx = 0; columnIdx < gridSize; columnIdx++) {
                int leftEdgeIdx;
                int rightEdgeIdx;
                if (rowIdx % 4 == 0) {
                    // Means we have reached bottom of grid
                    if (actualRowIdx == gridSize) {
                        int bottomIdx = board.cellsArray[actualRowIdx - 1][columnIdx][3];
                        // Checking if a player has already crossed this edge
                        if (movesPlayer1.contains(bottomIdx)) {
                            printBoard.append(board.BLUE_COLOR + ".______" + bottomIdx + "______." + board.COLOR_RESET);
                        } else if (movesPlayer2.contains(bottomIdx)) {
                            printBoard.append(board.RED_COLOR + ".______" + bottomIdx + "______." + board.COLOR_RESET);
                            // Else do not color the edge
                        } else {
                            printBoard.append(".______" + bottomIdx + "______.");
                        }
                        continue;
                    } else {
                        int topIdx = board.cellsArray[actualRowIdx][columnIdx][0];
                        if (topIdx < 10) {
                            if (movesPlayer1.contains(topIdx)) {
                                printBoard.append(board.BLUE_COLOR + ".______0" + topIdx + "______." + board.COLOR_RESET);
                            } else if (movesPlayer2.contains(topIdx)) {
                                printBoard.append(board.RED_COLOR + ".______0" + topIdx + "______." + board.COLOR_RESET);
                                // Else do not color the edge
                            } else {
                                printBoard.append(".______0" + topIdx + "______.");
                            }
                        } else {
                            if (movesPlayer1.contains(topIdx)) {
                                printBoard.append(board.BLUE_COLOR + ".______" + topIdx + "______." + board.COLOR_RESET);
                            } else if (movesPlayer2.contains(topIdx)) {
                                printBoard.append(board.RED_COLOR + ".______" + topIdx + "______." + board.COLOR_RESET);
                                // Else do not color the edge
                            } else {
                                printBoard.append(".______" + topIdx + "______.");
                            }
                        }
                    }
                }

                if (rowIdx % 2 == 0 && rowIdx % 4 != 0) {
                    leftEdgeIdx = board.cellsArray[actualRowIdx][columnIdx][1];
                    if (leftEdgeIdx < 10) {
                        if (movesPlayer1.contains(leftEdgeIdx)) {
                            printBoard.append(board.BLUE_COLOR + "0" + leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1 + board.COLOR_RESET);
                        } else if (movesPlayer2.contains(leftEdgeIdx)) {
                            printBoard.append(board.RED_COLOR + "0" + leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1 + board.COLOR_RESET);
                            // Else do not color the edge
                        } else {
                            printBoard.append("0" + leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1);
                        }
                        i++;
                    } else {
                        if (movesPlayer1.contains(leftEdgeIdx)) {
                            printBoard.append(board.BLUE_COLOR + leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1 + board.COLOR_RESET);
                        } else if (movesPlayer2.contains(leftEdgeIdx)) {
                            printBoard.append(board.RED_COLOR + leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1  + board.COLOR_RESET);
                            // Else do not color the edge
                        } else {
                            printBoard.append(leftEdgeIdx + board.SPACE + board.boxes[i] + board.SPACE1);
                        }
                        i++;
                    }
                    if (columnIdx == gridSize - 1) {
                        rightEdgeIdx = board.cellsArray[actualRowIdx][columnIdx][2];
                        if (movesPlayer1.contains(rightEdgeIdx)) {
                            printBoard.append(board.BLUE_COLOR + rightEdgeIdx + board.COLOR_RESET);
                        } else if (movesPlayer2.contains(rightEdgeIdx)) {
                            printBoard.append(board.RED_COLOR + rightEdgeIdx + board.COLOR_RESET);
                            // Else do not color the edge
                        } else {
                            printBoard.append(rightEdgeIdx);
                        }
                    }
                }else if (rowIdx % 2 != 0 && rowIdx % 4 != 0) {
                    printBoard.append("|               ");
                    if (columnIdx == gridSize - 1) {
                        printBoard.append("|");
                    }
                }
            }
            printBoard.append("\n");
            // If statement to check that we reached a "top edge"
            if (((rowIdx + 1) % 4 == 0) && rowIdx != 0) {
                actualRowIdx++;
            }
        }
        System.out.println(printBoard);
    }

    public int getNumEdges() {
        return this.board.numEdges;
    }

    /**
     * Gets the current player's turn.
     *
     * @return The name of the next player.
     */
    public static String getTurn(String currentPlayer, int result) {
        String nextPlayer = "";
        if(result == 1 || result ==3){
            nextPlayer = currentPlayer;
        }else{
            if (currentPlayer.equals(player1Name)) {
                nextPlayer = player2Name;
            } else {
                nextPlayer = player1Name;
            }
            currentPlayer = nextPlayer;
            board.setCurrentPlayer(currentPlayer);
        }
        return nextPlayer;
    }

    public Player getPlayer2(){
        return this.player2;
    }
}