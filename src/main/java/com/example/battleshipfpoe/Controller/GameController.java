package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;

public class GameController {

    @FXML
    private AnchorPane gameBoardPane;
    @FXML
    private AnchorPane enemyBoardPane; // Tablero del enemigo

    private BoardHandler boardHandler;
    private BoardHandler enemyBoardHandler;

    public void initialize() {
        double planeWidth = 400;
        double planeHeight = 400;
        int gridSize = 10;

        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, gameBoardPane);
        boardHandler.updateGrid();

        enemyBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, enemyBoardPane);
        enemyBoardHandler.updateGrid();
        placeEnemyShipsRandomly();
    }

    public void setBoatsList(List<Boat> boatsList) {
        // Iterate over each boat in the list
        for (Boat boat : boatsList) {
            int[] position = boat.getPosition();  // Get the boat's position (row, col)
            int row = position[0];
            int col = position[1];

            // Place the boat on the grid depending on its orientation
            if (boat.isHorizontal()) {
                for (int i = 0; i < boat.getLength(); i++) {
                    boardHandler.placeShip(row, col + i);  // Place each cell for horizontal boat
                }
            } else {
                for (int i = 0; i < boat.getLength(); i++) {
                    boardHandler.placeShip(row + i, col);  // Place each cell for vertical boat
                }
            }
        }

        // After placing all boats, update the grid
        boardHandler.updateGrid();
        setupCellInteractions();  // Ensure all cells are interactive
    }

    /*public void setBoatPositions(List<int[]> boatPositions) {
        // Limpiar las posiciones previas si es necesario, o simplemente actualizarlas
        for (int[] position : boatPositions) {
            int row = position[0];
            int col = position[1];

            // Assuming you have a way to know the size and orientation of the boat from the stored positions
            // Example: You could have a list of boat objects (with size and orientation) to place them
            Boat boat = getBoatForPosition(row, col);  // Retrieve boat based on row and col
            if (boat != null) {
                // Place the boat on the grid depending on its orientation
                if (boat.isHorizontal()) {
                    // Place the boat horizontally
                    for (int i = 0; i < boat.getLength(); i++) {
                        boardHandler.placeShip(row, col + i); // Place each cell for horizontal boat
                    }
                } else {
                    // Place the boat vertically
                    for (int i = 0; i < boat.getLength(); i++) {
                        boardHandler.placeShip(row + i, col); // Place each cell for vertical boat
                    }
                }
            }
        }

        // Update the grid to reflect the new boat positions
        boardHandler.updateGrid();
        setupCellInteractions();
    } */



        private void placeEnemyShipsRandomly() {
        Random rand = new Random();
        int numShips = 2;
        int count = 0;

        while (count < numShips) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);

            if (enemyBoardHandler.getCell(x, y) == 0) {
                enemyBoardHandler.setCell(x, y, 1);
                count++;
            }
        }
        enemyBoardHandler.updateGrid();
    }

// El tablero enemigo muestra las naves, aun no se como ocultarlas.
    /*private void hideEnemyShips() {
        for (var node : enemyBoardPane.getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                int[] cellCoords = (int[]) cell.getUserData();
                int row = cellCoords[0];
                int col = cellCoords[1];

                if (enemyBoardHandler.getCell(row, col) == 1) {
                    Rectangle invisibleLayer = new Rectangle(cell.getWidth(), cell.getHeight());
                    invisibleLayer.setFill(Color.TRANSPARENT);
                    cell.getChildren().add(invisibleLayer);
                }
            }
        }
    }*/


    private void setupCellInteractions() {
        for (var node : gameBoardPane.getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                int[] cellCoords = (int[]) cell.getUserData();
                int row = cellCoords[0];
                int col = cellCoords[1];

                cell.setOnMouseClicked(event -> {
                    if (boardHandler.getCell(row, col) == 1) {
                        boardHandler.registerHit(row, col);
                    } else {
                        boardHandler.registerMiss(row, col);
                    }
                    boardHandler.updateGrid();
                });
            }
        }
        for (var node : enemyBoardPane.getChildren()) {
            if (node instanceof Pane) {
                Pane cell = (Pane) node;
                int[] cellCoords = (int[]) cell.getUserData();
                int row = cellCoords[0];
                int col = cellCoords[1];

                cell.setOnMouseClicked(event -> {
                    if (enemyBoardHandler.getCell(row, col) == 1) {
                        enemyBoardHandler.registerHit(row, col);
                    } else {
                        enemyBoardHandler.registerMiss(row, col);
                    }
                    enemyBoardHandler.updateGrid();
                });
            }
        }
    }



}
