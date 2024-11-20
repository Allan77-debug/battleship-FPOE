package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;

public class GameController {

    @FXML
    private AnchorPane PlayerBoardPane;
    @FXML
    private AnchorPane enemyBoardPane; // Tablero del enemigo

    private BoardHandler PlayerHandler;
    private BoardHandler enemyBoardHandler;

    private int playerCount;
    private int enemyCount;

    private boolean isBoardRevealed;
    private boolean isEnemyTurn = true;

    private boolean endGame = false;

    public void initialize() {
        double planeWidth = 400;
        double planeHeight = 400;
        int gridSize = 10;

        PlayerHandler = new BoardHandler(planeWidth, planeHeight, gridSize, PlayerBoardPane);
        PlayerHandler.updateGrid(false);

        enemyBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, enemyBoardPane);
        enemyBoardHandler.updateGrid(true);
        isBoardRevealed = false;
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
                    PlayerHandler.placeShip(row, col + i);  // Place each cell for horizontal boat
                }
            } else {
                for (int i = 0; i < boat.getLength(); i++) {
                    PlayerHandler.placeShip(row + i, col);  // Place each cell for vertical boat
                }
            }
            playerCount++;
        }

        // After placing all boats, update the grid
        PlayerHandler.updateGrid(false);
        setupCellInteractions();  // Ensure all cells are interactive
    }



    private void placeEnemyShipsRandomly() {
        Random rand = new Random();
        int numShips = 2; // Número de barcos a colocar

        // Obtén la matriz de celdas del tablero enemigo
        ArrayList<ArrayList<Integer>> enemyGrid = enemyBoardHandler.getBoard();

        for (int i = 0; i < numShips; i++) {
            int x = rand.nextInt(enemyBoardHandler.getGridSize()); // Coordenada x aleatoria
            int y = rand.nextInt(enemyBoardHandler.getGridSize()); // Coordenada y aleatoria

            // Verifica si la celda está vacía (0 representa agua)
            if (enemyGrid.get(x).get(y) == 0) {
                enemyGrid.get(x).set(y, 1); // 1 representa un barco
        }
            enemyCount++;
        }
        setupCellInteractions();
    }

    private void setupCellInteractions() {
        if (isEnemyTurn) {
            setupBoardInteractions(PlayerBoardPane, PlayerHandler, false);
        }
        else {
            setupBoardInteractions(enemyBoardPane, enemyBoardHandler, true);
        }
    }

    private void setupBoardInteractions(Pane boardPane, BoardHandler handler, boolean isEnemyBoard) {
        for (var node : boardPane.getChildren()) {
            if (node instanceof Pane cell) {
                int[] cellCoords = (int[]) cell.getUserData();
                int row = cellCoords[0];
                int col = cellCoords[1];

                cell.setOnMouseClicked(event -> handleTurn(handler, row, col, isEnemyBoard));
            }
        }
    }

    private void handleTurn(BoardHandler handler, int row, int col, boolean isEnemyBoard) {
        if (endGame) return; // Stop if the game is over

        if (isEnemyTurn && !isEnemyBoard) {
            System.out.println("Enemy's turn!");
            if (handler.getCell(row, col) == 1) {
                handler.registerHit(row, col);
                playerCount--;
                System.out.println(playerCount);
            } else {
                handler.registerMiss(row, col);
            }
            isEnemyTurn = false;
            System.out.println("End enemy's turn");// End enemy's turn
            handler.updateGrid(false);
        }
        else if (!isEnemyTurn) {
            System.out.println("Player's turn!");
            if (handler.getCell(row, col) == 1) {
                handler.registerHit(row, col);
                enemyCount--;
                System.out.println(enemyCount);
            } else {
                handler.registerMiss(row, col);
            }
            isEnemyTurn = true;
            System.out.println("End player's turn");
            handler.updateGrid(true);
        }
        else {
            System.out.println("Invalid board clicked.");
        }
        checkForEndGame();
    }

    private void checkForEndGame() {
        if (playerCount == 0 || enemyCount == 0) {
            endGame = true;
            System.out.println("Game Over!");
            // Add any additional game-ending logic here (e.g., disable clicks)
        }
        else {
            setupCellInteractions();

        }
    }



    public void handleRevealBoard(ActionEvent event) {
        enemyBoardHandler.updateGrid(!isBoardRevealed); // Toggle between hidden and revealed
        isBoardRevealed = !isBoardRevealed; // Flip the state
        System.out.println("Board reveal toggled: " + (isBoardRevealed ? "Revealed" : "Hidden"));
    }



}

