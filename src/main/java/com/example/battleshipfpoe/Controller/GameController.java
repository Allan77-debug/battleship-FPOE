package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
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



    public void setBoatPositions(List<int[]> boatPositions) {
        // Limpiar las posiciones previas si es necesario, o simplemente actualizarlas
        for (int[] position : boatPositions) {
            int row = position[0];
            int col = position[1];

            boardHandler.placeShip(row, col);

        }
        boardHandler.updateGrid();
        setupCellInteractions();
    }

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
