package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardHandler extends BoardBase {

    private static final Color BACKGROUND_COLOR_1 = Color.rgb(127, 205, 255);
    private static final Color BACKGROUND_COLOR_2 = Color.rgb(6, 66, 115);
    private static final Color SHIP_COLOR = Color.GRAY;
    private static final Color HIT_COLOR = Color.RED;
    private static final Color MISS_COLOR = Color.BLUE;

    /**
     * Constructor for BoardHandler.
     *
     * @param planeWidth  the width of the board
     * @param planeHeight the height of the board
     * @param gridSize    the size of each grid square
     * @param anchorPane  the JavaFX AnchorPane to render the board
     */
    public BoardHandler(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);
    }

    /**
     * Updates the grid visually based on the current board state.
     */
    public void updateGrid() {
        // Clear the previous grid visuals
        getAnchorPane().getChildren().clear();

        // Iterate over the board and create rectangles for each tile
        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                int tileValue = getCell(row, col);

                // Create a rectangle for the current cell
                Rectangle rectangle = new Rectangle(
                        col * getTilesAcross(),   // X position
                        row * getTilesDown(),    // Y position
                        getTilesAcross(),        // Width
                        getTilesDown()           // Height
                );

                // Set the color based on the tile's value
                rectangle.setFill(determineTileColor(tileValue));
                rectangle.setStroke(Color.BLACK);  // Add a black border
                rectangle.setStrokeWidth(1);

                // Add the rectangle to the anchor pane
                getAnchorPane().getChildren().add(rectangle);
            }
        }
    }

    /**
     * Determines the color for a given tile value.
     *
     * @param tileValue the value of the tile
     * @return the corresponding color
     */
    private Color determineTileColor(int tileValue) {
        switch (tileValue) {
            case 1:  // Ship
                return SHIP_COLOR;
            case 2:  // Hit
                return HIT_COLOR;
            case -1: // Miss
                return MISS_COLOR;
            default: // Water
                return BACKGROUND_COLOR_1;  // Alternating pattern can be added if needed
        }
    }

    /**
     * Places a ship on the board at the specified position.
     *
     * @param row the row index
     * @param col the column index
     */
    public void placeShip(int row, int col) {
        setCell(row, col, 1);  // 1 represents a ship
    }

    /**
     * Registers a hit on the board.
     *
     * @param row the row index
     * @param col the column index
     */
    public void registerHit(int row, int col) {
        setCell(row, col, 2);  // 2 represents a hit
    }

    /**
     * Registers a miss on the board.
     *
     * @param row the row index
     * @param col the column index
     */
    public void registerMiss(int row, int col) {
        setCell(row, col, -1);  // -1 represents a miss
    }
}
