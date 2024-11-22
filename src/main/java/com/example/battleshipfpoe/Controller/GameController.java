package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import com.example.battleshipfpoe.Model.SaveSystem.*;
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

    private GameStateManager gameStateManager;
    private String saveFilePath = "gameState.ser";

    private BoardHandler playerBoardHandler;
    private BoardHandler enemyBoardHandler;

    private int playerCount = 0;
    private int enemyCount = 0;

    private boolean isBoardRevealed = false;
    private boolean isEnemyTurn = false;

    private boolean endGame = false;

    public void initialize() {
        SaveInterface<GameProgress> serializedHandler = new SerializedSaveHandler<>();
        SaveSystem<GameProgress> saveSystem = new SaveSystem<>(serializedHandler);
        gameStateManager = new GameStateManager(saveSystem);
    }

    public void setBoatsList(List<Boat> boatsList) {
        for (Boat boat : boatsList) {
            placeBoat(boat);
        }
        updateBoardState();
    }

    // Method to handle the placement of a single boat
    private void placeBoat(Boat boat) {
        int[] position = boat.getPosition();
        int row = position[0];
        int col = position[1];
        boolean isHorizontal = boat.isHorizontal();

        if (canPlaceBoat(row, col, boat.getLength(), isHorizontal)) {
            placeShip(row, col, boat.getLength(), isHorizontal);
            playerCount += boat.getLength();
        } else {
            System.out.println("Could not place the boat at (" + row + ", " + col + ")");
        }
    }
    private boolean canPlaceBoat(int row, int col, int boatLength, boolean isHorizontal) {
        return playerBoardHandler.canPlaceShip(row, col, boatLength, isHorizontal);
    }

    // Place the boat on the grid
    private void placeShip(int row, int col, int size, boolean horizontal) {
        playerBoardHandler.placeShip(row, col, size, horizontal);
    }

    // Update the grid and setup cell interactions after placing all boats
    private void updateBoardState() {
        playerBoardHandler.updateGrid(false);
        setupCellInteractions();
    }

    /**
     * Coloca los barcos de la máquina de forma aleatoria.
     */
    private void placeEnemyShipsRandomly() {
        Random rand = new Random();
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // Tamaños de barcos

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

        isBoardRevealed = false;
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
        int gridSize = playerBoardHandler.getGridSize();

        while (!validShot) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);

            if (!playerBoardHandler.isCellAlreadyShot(x, y)) {
                validShot = true;

                if (playerBoardHandler.getCell(x, y) == 1) {
                    playerBoardHandler.registerHit(x, y);
                    playerCount--;
                    System.out.println("La máquina acertó en (" + x + ", " + y + ")");
                } else {
                    playerBoardHandler.registerMiss(x, y);
                    System.out.println("La máquina falló en (" + x + ", " + y + ")");
                }

                playerBoardHandler.updateGrid(false);
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
        saveGameState();
    }

    /**
     * Maneja el evento de revelar el tablero del enemigo.
     */
    public void handleRevealBoard(ActionEvent event) {
        enemyBoardHandler.updateGrid(isBoardRevealed);
        isBoardRevealed = !isBoardRevealed;
        System.out.println("Tablero revelado: " + (isBoardRevealed ? "Visible" : "Oculto"));
        setupCellInteractions();
    }


    public void saveGameState() {
        GameProgress gameProgress = new GameProgress(
                playerBoardHandler,
                enemyBoardHandler,
                playerBoardHandler.getBoard(),
                enemyBoardHandler.getBoard(),
                playerCount,
                enemyCount,
                isEnemyTurn,
                endGame
        );
        gameStateManager.saveGame(gameProgress,saveFilePath);
    }
    public void newGameState() {
        double planeWidth = 400;
        double planeHeight = 400;
        int gridSize = 10;

        this.playerBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, PlayerBoardPane);
        playerBoardHandler.updateGrid(false);

        this.enemyBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, enemyBoardPane);
        enemyBoardHandler.updateGrid(true);
        // Place enemy ships
        placeEnemyShipsRandomly();
    }

    public void loadGameState() {
        // Load the saved game progress using GameStateManager
        GameProgress gameProgress = gameStateManager.loadGame(saveFilePath);
        if (gameProgress == null) {
            System.out.println("No saved game to load.");
            return; // Exit if there's no saved game data
        }

        gameProgress.getPlayerBoardHandler().setBoard(gameProgress.getPlayerBoard(),PlayerBoardPane);
        gameProgress.getEnemyBoardHandler().setBoard(gameProgress.getEnemyBoard(),enemyBoardPane);
        // Restore game state variables
        this.playerBoardHandler = gameProgress.getPlayerBoardHandler();
        this.enemyBoardHandler = gameProgress.getEnemyBoardHandler();
        this.playerCount = gameProgress.getPlayerCount();
        this.enemyCount = gameProgress.getEnemyCount();
        this.isEnemyTurn = gameProgress.isEnemyTurn();
        this.endGame = gameProgress.isGameEnded();


        playerBoardHandler.updateGrid(false);
        enemyBoardHandler.updateGrid(true);

        // Print game state for confirmation
        System.out.println("Game state loaded successfully!");
        System.out.println("Player boats left: " + playerCount);
        System.out.println("Enemy boats left: " + enemyCount);
        System.out.println("Is enemy's turn: " + isEnemyTurn);
        System.out.println("Game ended: " + endGame);
        // Set up the board interactions again
        setupCellInteractions();
    }
}