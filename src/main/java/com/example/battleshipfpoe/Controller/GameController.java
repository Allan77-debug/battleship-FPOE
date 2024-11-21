package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
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
    private AnchorPane enemyBoardPane;

    private BoardHandler PlayerHandler;
    private BoardHandler enemyBoardHandler;

    private int playerCount = 0;
    private int enemyCount = 0;

    private boolean isBoardRevealed = false;
    private boolean isEnemyTurn = false;

    private boolean endGame = false;

    public void initialize() {
        double planeWidth = 400;
        double planeHeight = 400;
        int gridSize = 10;

        // Inicializar los tableros de jugador y enemigo
        PlayerHandler = new BoardHandler(planeWidth, planeHeight, gridSize, PlayerBoardPane);
        PlayerHandler.updateGrid(false);

        enemyBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, enemyBoardPane);
        enemyBoardHandler.updateGrid(true);

        // Colocar barcos de la máquina
        placeEnemyShipsRandomly();
    }

    public void setBoatsList(List<Boat> boatsList) {
        for (Boat boat : boatsList) {
            int[] position = boat.getPosition(); // Obtén la posición inicial del barco
            int row = position[0];
            int col = position[1];
            boolean isHorizontal = boat.isHorizontal();

            // Usa el método actualizado de BoardHandler
            if (PlayerHandler.canPlaceShip(row, col, boat.getLength(), isHorizontal)) {
                PlayerHandler.placeShip(row, col, boat.getLength(), isHorizontal);
                playerCount += boat.getLength(); // Actualiza el contador total de celdas ocupadas por barcos
            } else {
                System.out.println("No se pudo colocar el barco en (" + row + ", " + col + ")");
            }
        }

        PlayerHandler.updateGrid(false); // Actualiza el tablero después de colocar los barcos
        setupCellInteractions(); // Configura las interacciones de las celdas
    }

    /**
     * Coloca los barcos de la máquina de forma aleatoria.
     */
    private void placeEnemyShipsRandomly() {
        Random rand = new Random();
        int[] shipSizes = {5, 4, 3, 3, 2}; // Tamaños de barcos

        for (int size : shipSizes) {
            boolean placed = false;

            while (!placed) {
                int startX = rand.nextInt(enemyBoardHandler.getGridSize());
                int startY = rand.nextInt(enemyBoardHandler.getGridSize());
                boolean horizontal = rand.nextBoolean();

                if (enemyBoardHandler.canPlaceShip(startX, startY, size, horizontal)) {
                    enemyBoardHandler.placeShip(startX, startY, size, horizontal);
                    placed = true;
                    enemyCount += size;
                }
            }
        }
    }

    /**
     * Configura las interacciones con las celdas del tablero según el turno.
     */
    private void setupCellInteractions() {
        if (endGame) return;

        if (!isEnemyTurn) {
            setupBoardInteractions(enemyBoardPane, enemyBoardHandler, true);
        } else {
            machineTurn();
        }
    }

    /**
     * Configura las interacciones en un tablero.
     */
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

    /**
     * Maneja el turno del jugador o de la máquina según el estado del juego.
     */
    private void handleTurn(BoardHandler handler, int row, int col, boolean isEnemyBoard) {
        if (endGame || isEnemyTurn || handler.isCellAlreadyShot(row, col)) return;

        if (handler.getCell(row, col) == 1) {
            handler.registerHit(row, col);
            enemyCount--;
            System.out.println("¡Acertaste!");
        } else {
            handler.registerMiss(row, col);
            System.out.println("¡Fallaste!");
        }

        handler.updateGrid(true);
        isEnemyTurn = true; // Cambia al turno de la máquina
        checkForEndGame();
        setupCellInteractions();
    }

    /**
     * Turno de la máquina para disparar aleatoriamente.
     */
    private void machineTurn() {
        if (endGame) return;

        Random rand = new Random();
        boolean validShot = false;
        int gridSize = PlayerHandler.getGridSize();

        while (!validShot) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);

            if (!PlayerHandler.isCellAlreadyShot(x, y)) {
                validShot = true;

                if (PlayerHandler.getCell(x, y) == 1) {
                    PlayerHandler.registerHit(x, y);
                    playerCount--;
                    System.out.println("La máquina acertó en (" + x + ", " + y + ")");
                } else {
                    PlayerHandler.registerMiss(x, y);
                    System.out.println("La máquina falló en (" + x + ", " + y + ")");
                }

                PlayerHandler.updateGrid(false);
            }
        }

        isEnemyTurn = false; // Cambia al turno del jugador
        checkForEndGame();
        setupCellInteractions();
    }

    /**
     * Verifica si el juego ha terminado.
     */
    private void checkForEndGame() {
        if (playerCount == 0 || enemyCount == 0) {
            endGame = true;
            System.out.println("¡Juego Terminado! " + (playerCount == 0 ? "La máquina ganó." : "¡Ganaste!"));
        }
    }

    /**
     * Maneja el evento de revelar el tablero del enemigo.
     */
    public void handleRevealBoard(ActionEvent event) {
        enemyBoardHandler.updateGrid(!isBoardRevealed);
        isBoardRevealed = !isBoardRevealed;
        System.out.println("Tablero revelado: " + (isBoardRevealed ? "Visible" : "Oculto"));
    }
}
