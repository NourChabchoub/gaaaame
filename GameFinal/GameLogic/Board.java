package GameLogic;

import java.util.Arrays;

/**
 * Represents the game board for a Dot and Boxes game.
 */

public class Board {
    public int[][][] cellsArray;
    public boolean[] edgesArray;
    public int numEdges;
    String currentPlayer;

    //Dimension of the board
    public int gridSize;
    String blackSquare = "\u001B[30m\u25A0\u001B[0m";
    String redSquare = "\u001B[31m\u25A0\u001B[0m";
    String blueSquare = "\u001B[34m\u25A0\u001B[0m";
    String[] boxes;
    public String SPACE = "       ";
    public String SPACE1 = "      ";
    public final String RED_COLOR = "\u001B[31m";
    public final String BLUE_COLOR = "\u001B[34m";
    public final String COLOR_RESET = "\u001B[0m";
    public int playerOneBoxes = 0;
    public int playerTwoBoxes = 0;
    private String player1name;
    private String player2name;

    //size of the grid: the number of cells in a row
    private static int size;
    //    public static final int gridSize = ClientTUI.getSize(size);

    /**
     * Sets up the grid for the game board with given cellsArray.
     *
     * @param inputGridSize Represents the grid size of te game board.
     */

    public Board(int inputGridSize, String player1, String player2) {

        this.gridSize = inputGridSize;
        //Applied a formula to compute the number of unique edges in a square grid
        this.numEdges = (2 * (gridSize + 1) * (gridSize + 1)) - 2 * (gridSize + 1);

        //Rows, Columns and Edges
        this.cellsArray = new int[gridSize][gridSize][5];
        //Empty array to store edge status (False = not filled, True = filled)
        this.edgesArray = new boolean[numEdges];
        //Setting them all to False as the new game begins
        Arrays.fill(this.edgesArray, Boolean.FALSE);
        this.player1name = player1;
        this.player2name = player2;
        this.currentPlayer = player1;
        this.boxes = new String[inputGridSize*inputGridSize];
    }


    public void setupGrid() {
        //To go through each row
        for (int rowIdx = 0; rowIdx < this.gridSize; rowIdx++) {
            //To go through each column
            for (int columnIdx = 0; columnIdx < this.gridSize; columnIdx++) {

                //Calculating the indices to match the dimension given in the TUI
                int topEgdeIdx = rowIdx * (gridSize * 2 + 1) + columnIdx;
                int bottomEdgeIdx = topEgdeIdx + (gridSize * 2 + 1);
                int leftEdgeIdx = gridSize + ((gridSize * 2 + 1) * rowIdx) + columnIdx;
                int rightEdgeIdx = leftEdgeIdx + 1;

                //Cell array: top edge, left edge, right edge and bottom edge
                this.cellsArray[rowIdx][columnIdx][0] = topEgdeIdx;
                this.cellsArray[rowIdx][columnIdx][1] = leftEdgeIdx;
                this.cellsArray[rowIdx][columnIdx][2] = rightEdgeIdx;
                this.cellsArray[rowIdx][columnIdx][3] = bottomEdgeIdx;

            }
        }
        Arrays.fill(boxes, blackSquare);
    }


    /**
     * Counts and returns the number of filled boxes on the game board.
     *
     * @return The number of filled boxes.
     */
    public int getNumberFilledBoxes() {
        //Counter for the filed boxes
        int boxesCounter = 0;
        int i = 0;

        for (int rowIdx = 0; rowIdx < this.gridSize; rowIdx++) {
            for (int columnIdx = 0; columnIdx < this.gridSize; columnIdx++) {
                //Extract the indices from the edges of the current box
                int topIdx = this.cellsArray[rowIdx][columnIdx][0];
                int leftIdx = this.cellsArray[rowIdx][columnIdx][1];
                int rightIdx = this.cellsArray[rowIdx][columnIdx][2];
                int bottomIdx = this.cellsArray[rowIdx][columnIdx][3];

                //True indicates that the edge is filled, so if the four edges are filled (true) it will add +1 to the counter
                if (this.edgesArray[topIdx] && this.edgesArray[leftIdx] && this.edgesArray[rightIdx] && this.edgesArray[bottomIdx]) {
                    if (currentPlayer.equals(player1name) && this.boxes[i].equals(blackSquare)){
                        this.boxes[i]=blueSquare;
                    } else if (currentPlayer.equals(player2name) && this.boxes[i].equals(blackSquare)){
                        this.boxes[i]=redSquare;
                    } boxesCounter++;
                }
                i++;
            }
        } return boxesCounter;

    }

    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }
}