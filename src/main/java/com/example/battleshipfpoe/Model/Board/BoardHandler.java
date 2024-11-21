package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

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
        getAnchorPane().getChildren().clear(); // Limpiar el tablero antes de redibujarlo

        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                // Crear una nueva celda
                Pane cell = new Pane();
                cell.setPrefSize(getTilesAcross(), getTilesDown());
                cell.setLayoutX(col * getTilesAcross());
                cell.setLayoutY(row * getTilesDown());
                cell.setStyle("-fx-border-color: black; -fx-background-color: transparent;"); // Fondo transparente por defecto

                int tileValue = getCell(row, col);

                // Si el tablero está oculto
                if (isBoardHidden) {
                    if (tileValue == 1) { // Si es un barco
                        // Mantener la celda transparente
                        cell.setStyle("-fx-border-color: black; -fx-background-color: transparent;");
                    }
                    // Mostrar siempre los hits y misses
                    else if (tileValue == 2 || tileValue == -1) { // Hit o Miss
                        Color tileColor = determineTileColor(tileValue);
                        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
                    }
                } else {
                    // Si el tablero no está oculto
                    Color tileColor = determineTileColor(tileValue);

                    if (tileValue == 1) { // Barco
                        // Si es un barco, lo pintamos de gris o el color que determines
                        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
                    } else if (tileValue == 2 || tileValue == -1) { // Hit o Miss
                        // Si es un hit o miss, pintamos con el color adecuado
                        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
                    }
                }

                // Agregar identificadores para la celda
                cell.setUserData(new int[]{row, col});
                getAnchorPane().getChildren().add(cell);
            }
        }
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

    public void highlightCells(List<int[]> candidateCells, Color highlightColor) {
        for (Node node : getAnchorPane().getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                int[] cellPosition = (int[]) cell.getUserData();

                for (int[] candidate : candidateCells) {
                    if (cellPosition[0] == candidate[0] && cellPosition[1] == candidate[1]) {
                        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(highlightColor) + ";");
                    }
                }
            }
        }
    }

    public void clearHighlights() {
        for (Node node : getAnchorPane().getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                cell.setStyle("-fx-border-color: black; -fx-background-color: transparent;");
            }
        }
    }


}
