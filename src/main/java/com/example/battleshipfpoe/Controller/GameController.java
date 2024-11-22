package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import com.example.battleshipfpoe.Model.SaveSystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

/**
 * This controller handles the logic for the game, including managing player and enemy boards,
 * placing ships, handling turns, saving and loading game state, and detecting when the game ends.
 */
public class GameController {
    @FXML
    private Text PlayerText;
    @FXML
    private AnchorPane PlayerBoardPane;
    @FXML
    private AnchorPane enemyBoardPane;
    @FXML
    private AnchorPane testPane;

    private SerializedGameStateManager serializedGameStateManager;
    private final String SerializedSaveFilePath = "SerializedGameState.ser";

    private PlainTextGameStateManager plainTextGameStateManager;
    private final String TextSaveFilePath = "TextGameState.txt";

    private BoardHandler playerBoardHandler;
    private BoardHandler enemyBoardHandler;

    private List<Boat> playerBoatsList;
    private int playerCount = 0;
    private int enemyCount = 0;

    private boolean isBoardRevealed = false;
    private boolean isEnemyTurn = false;

    private boolean endGame = false;
    private String playerName = "";

    /**
     * Initializes the game controller, setting up the serialized and plain text game state managers.
     */
    public void initialize() {
        SaveInterface<GameProgress> serializedHandler = new SerializedSaveHandler<>();
        SaveSystem<GameProgress> serializedSaveSystem = new SaveSystem<>(serializedHandler);
        serializedGameStateManager = new SerializedGameStateManager(serializedSaveSystem);
        PlainTextSaveHandler plainTextHandler = new PlainTextSaveHandler();
        SaveSystem<String> textSaveSystem = new SaveSystem<>(plainTextHandler);
        plainTextGameStateManager = new PlainTextGameStateManager(textSaveSystem);
    }

    /**
     * Sets the list of boats for the player and places them on the board.
     *
     * @param boatsList a list of boats to place on the player's board
     */
    public void setBoatsList(List<Boat> boatsList) {
        for (Boat boat : boatsList) {
            placeBoat(boat);
        }
        updateBoardState();
    }

    /**
     * Handles the placement of a single boat on the player's board.
     *
     * @param boat the boat to be placed on the board
     */
    private void placeBoat(Boat boat) {
        int[] position = boat.getPosition();
        int row = position[0];
        int col = position[1];
        boolean isHorizontal = boat.isHorizontal();

        // Place the boat logically on the board first
        if (canPlaceBoat(row, col, boat.getLength(), isHorizontal)) {
            placeShip(row, col, boat.getLength(), isHorizontal);
            playerCount += boat.getLength();
        } else {
            System.out.println("Could not place the boat at (" + row + ", " + col + ")");
        }
    }

    /**
     * Checks if the boat can be placed at the given coordinates and orientation.
     *
     * @param row the row index where the boat will be placed
     * @param col the column index where the boat will be placed
     * @param boatLength the length of the boat
     * @param isHorizontal whether the boat is placed horizontally or vertically
     * @return true if the boat can be placed, false otherwise
     */
    private boolean canPlaceBoat(int row, int col, int boatLength, boolean isHorizontal) {
        return playerBoardHandler.canPlaceShip(row, col, boatLength, isHorizontal);
    }

    /**
     * Places the boat on the player's board.
     *
     * @param row the row index where the boat will be placed
     * @param col the column index where the boat will be placed
     * @param size the size (length) of the boat
     * @param horizontal whether the boat is placed horizontally
     */
    private void placeShip(int row, int col, int size, boolean horizontal) {
        playerBoardHandler.placeShip(row, col, size, horizontal);
    }

    /**
     * Updates the grid and sets up cell interactions after all boats have been placed.
     */
    private void updateBoardState() {
        playerBoardHandler.updateGrid(false);
        setupCellInteractions();
    }

    /**
     * Places the enemy's ships randomly on the board.
     */
    private void placeEnemyShipsRandomly() {
        Random rand = new Random();
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // Boat sizes

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
     * Configures the interactions with the cells of the board depending on the player's or enemy's turn.
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
     * Configures the interactions on a specific board for cell clicks.
     *
     * @param boardPane the pane representing the board
     * @param handler the board handler for the board
     * @param isEnemyBoard whether the board is the enemy's board
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
     * Handles the player's or enemy's turn, depending on the state of the game.
     *
     * @param handler the board handler for the turn
     * @param row the row index of the cell to be shot at
     * @param col the column index of the cell to be shot at
     * @param isEnemyBoard whether the turn is against the enemy's board
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
        isEnemyTurn = true; // Switch to enemy's turn
        checkForEndGame();
        setupCellInteractions();
    }

    /**
     * Executes the enemy's turn, with the enemy shooting randomly at the player's board.
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

        isEnemyTurn = false; // Switch to player's turn
        checkForEndGame();
        setupCellInteractions();
    }

    /**
     * Checks if the game has ended. If either the player or enemy has no remaining boats, the game ends.
     */
    private void checkForEndGame() {
        if (playerCount == 0 || enemyCount == 0) {
            endGame = true;
            System.out.println("¡Juego Terminado! " + (playerCount == 0 ? "La máquina ganó." : "¡Ganaste!"));
        }
        saveGameState();
    }

    /**
     * Reveals or hides the enemy's board when the player clicks the "Reveal" button.
     *
     * @param event the action event triggered by the "Reveal" button click
     */
    public void handleRevealBoard(ActionEvent event) {
        enemyBoardHandler.updateGrid(isBoardRevealed);
        isBoardRevealed = !isBoardRevealed;
        System.out.println("Tablero revelado: " + (isBoardRevealed ? "Visible" : "Oculto"));
        setupCellInteractions();
    }

    /**
     * Saves the current game state, including both boards, the remaining boats, and the turn status.
     */
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
        serializedGameStateManager.saveGame(gameProgress, SerializedSaveFilePath);
    }

    /**
     * Starts a new game, initializing the boards and placing the enemy ships randomly.
     */
    public void newGameState() {
        double planeWidth = 400;
        double planeHeight = 400;
        int gridSize = 10;

        this.playerBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, PlayerBoardPane);
        playerBoardHandler.updateGrid(false);

        this.enemyBoardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, enemyBoardPane);
        enemyBoardHandler.updateGrid(true);
        placeEnemyShipsRandomly();
        PlayerText.setText(playerName.toUpperCase());
    }

    /**
     * Loads a previously saved game state, including the boards, boats, and game progress.
     */
    public void loadGameState() {
        // Load Plain Text Data
        String textData = plainTextGameStateManager.loadGame(TextSaveFilePath);
        if (textData != null) {
            this.playerName = textData;
            PlayerText.setText(playerName.toUpperCase());
        }
        // Load the saved game progress using GameStateManager
        GameProgress gameProgress = serializedGameStateManager.loadGame(SerializedSaveFilePath);
        if (gameProgress == null) {
            System.out.println("No saved game to load.");
            return;
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

        setupCellInteractions();
    }

    /**
     * Sets the player's name.
     *
     * @param playerName the name of the player
     */
    public void setPlayerText(String playerName) {
        this.playerName = playerName;
    }
}