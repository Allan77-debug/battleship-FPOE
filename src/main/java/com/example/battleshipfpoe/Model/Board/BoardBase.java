package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;

public abstract class BoardBase {

    private final double planeWidth;
    private final double planeHeight;
    private final int tilesAcross;
    private final int tileAmount;
    private final int gridSize;
    private final int tilesDown;
    private final AnchorPane anchorPane;
    private final ArrayList<ArrayList<Integer>> board;

    /**
     * Constructs a BoardBase object with the given parameters.
     *
     * @param planeWidth  the width of the plane
     * @param planeHeight the height of the plane
     * @param gridSize    the size of the grid (number of rows and columns)
     * @param anchorPane  the JavaFX AnchorPane to render the board
     */
    public BoardBase(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;
        this.gridSize = gridSize;
        this.anchorPane = anchorPane;

        tilesAcross = (int) (planeWidth / gridSize);
        tileAmount = (int) ((planeWidth / gridSize) * (planeHeight / gridSize));
        tilesDown = tileAmount / tilesAcross;

        // Initialize the board
        board = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < gridSize; j++) {
                row.addLast(0); // Default to 0 (water)
            }
            board.addLast(row);
        }
    }

    /**
     * Retrieves the value at a specific cell on the board.
     *
     * @param row the row index (0 to gridSize-1)
     * @param col the column index (0 to gridSize-1)
     * @return the value at the specified cell
     */
    public int getCell(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * Sets a value at a specific cell on the board.
     *
     * @param row   the row index (0 to gridSize-1)
     * @param col   the column index (0 to gridSize-1)
     * @param value the value to set
     */
    public void setCell(int row, int col, int value) {
        board.get(row).set(col, value);
    }

    /**
     * Resets the board, initializing all cells to 0 (water).
     */
    public void resetBoard() {
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Integer> row = board.get(i);
            for (int j = 0; j < gridSize; j++) {
                row.set(j, 0);
            }
        }
    }

    // Getters
    public double getPlaneWidth() {
        return planeWidth;
    }

    public double getPlaneHeight() {
        return planeHeight;
    }

    public int getTilesAcross() {
        return tilesAcross;
    }

    public int getTileAmount() {
        return tileAmount;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getTilesDown() {
        return tilesDown;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    /**
     * Prints the current state of the board for debugging.
     */
    public void printBoard() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(getCell(i, j) + " ");
            }
            System.out.println();
        }
    }
}
