package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class BoardHandler extends BoardBase {

    private static final Color BACKGROUND_COLOR_1 = Color.rgb(127, 205, 255);
    private static final Color BACKGROUND_COLOR_2 = Color.rgb(6, 66, 115);
    private static final Color SHIP_COLOR = Color.GRAY;
    private static final Color HIT_COLOR = Color.RED;
    private static final Color MISS_COLOR = Color.BLUE;

    private boolean initializeEmpty;

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
    public void updateGrid(boolean isBoardHidden) {
        clearBoard();
        drawGrid(isBoardHidden);
    }


    private void clearBoard() {
        getAnchorPane().getChildren().clear();
    }
    private void drawGrid(boolean isBoardHidden) {
        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                Pane cell = createCell(row, col, isBoardHidden);
                getAnchorPane().getChildren().add(cell); // Add the cell to the board
            }
        }
    }

    private Pane createCell(int row, int col, boolean isBoardHidden) {
        Pane cell = new Pane();
        cell.setPrefSize(getTilesAcross(), getTilesDown());
        cell.setLayoutX(col * getTilesAcross());
        cell.setLayoutY(row * getTilesDown());
        cell.setStyle("-fx-border-color: black; -fx-background-color: transparent;");

        int tileValue = getCell(row, col); // Get the value of the tile
        Color tileColor = getCellColor(tileValue, isBoardHidden);

        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
        cell.setUserData(new int[]{row, col}); // Set row and col as user data

        return cell;
    }

    private Color getCellColor(int tileValue, boolean isBoardHidden) {
        // If it's a hidden board and the tile is a ship, hide the ship
        if (isBoardHidden && tileValue == 1) {
            return BACKGROUND_COLOR_1; // Water (hides the ship)
        }
        return determineTileColor(tileValue); // Use the existing logic for color determination
    }
    /**
     * Determines the color for a given tile value.
     *
     * @param tileValue the value of the tile
     * @return the corresponding color
     */
    private Color determineTileColor(int tileValue) {
        final Map<Integer, Color> TILE_COLOR_MAP = Map.of(
                1, SHIP_COLOR,   // Ship
                2, HIT_COLOR,    // Hit
                -1, MISS_COLOR    // Miss
                // Water is the default case, see below
        ); // Alternating pattern can be added if needed
        return TILE_COLOR_MAP.getOrDefault(tileValue,BACKGROUND_COLOR_1);
    }

    public boolean isWithinBounds(int row, int col) {
        int gridSize = getGridSize();
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    /**
     * Convierte un color de JavaFX a formato RGB para poder usarlo en el estilo CSS.
     *
     * @param color el color a convertir
     * @return el color en formato RGB como cadena
     */
    private String toRgbString(Color color) {
        return "rgb(" + (int)(color.getRed() * 255) + "," +
                (int)(color.getGreen() * 255) + "," +
                (int)(color.getBlue() * 255) + ")";
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

    public boolean allShipsSunk() {
        return false;
    }
}
