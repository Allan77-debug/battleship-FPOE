package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.Serializable;
import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.List.ArrayList;

/**
 * Represents the game progress for the Battleship game.
 * This class is used to save and load the state of the game, including board states,
 * player and enemy counts, and turn information.
 */
public class GameProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BoardHandler playerBoardHandler; // Player's board handler
    private final BoardHandler enemyBoardHandler; // Enemy's board handler
    private ArrayList<ArrayList<Integer>> playerBoard; // Logical state of the player's board
    private ArrayList<ArrayList<Integer>> enemyBoard; // Logical state of the enemy's board
    private final int playerCount; // Remaining cells of player's ships
    private final int enemyCount; // Remaining cells of enemy's ships
    private final boolean isEnemyTurn; // Indicates if it is the enemy's turn
    private final boolean endGame; // Indicates if the game has ended

    /**
     * Constructs a new GameProgress instance.
     *
     * @param playerBoardHandler The player's BoardHandler.
     * @param enemyBoardHandler  The enemy's BoardHandler.
     * @param playerBoard        The logical state of the player's board.
     * @param enemyBoard         The logical state of the enemy's board.
     * @param playerCount        The number of cells occupied by the player's ships.
     * @param enemyCount         The number of cells occupied by the enemy's ships.
     * @param isEnemyTurn        True if it is the enemy's turn, false otherwise.
     * @param endGame            True if the game has ended, false otherwise.
     */
    public GameProgress(BoardHandler playerBoardHandler, BoardHandler enemyBoardHandler,
                        ArrayList<ArrayList<Integer>> playerBoard, ArrayList<ArrayList<Integer>> enemyBoard,
                        int playerCount, int enemyCount, boolean isEnemyTurn, boolean endGame) {
        this.playerBoardHandler = playerBoardHandler;
        this.enemyBoardHandler = enemyBoardHandler;
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.playerCount = playerCount;
        this.enemyCount = enemyCount;
        this.isEnemyTurn = isEnemyTurn;
        this.endGame = endGame;
    }

    /**
     * Gets the player's BoardHandler.
     *
     * @return The player's BoardHandler.
     */
    public BoardHandler getPlayerBoardHandler() {
        return playerBoardHandler;
    }

    /**
     * Gets the enemy's BoardHandler.
     *
     * @return The enemy's BoardHandler.
     */
    public BoardHandler getEnemyBoardHandler() {
        return enemyBoardHandler;
    }

    /**
     * Gets the logical state of the player's board.
     *
     * @return The player's board as a 2D ArrayList.
     */
    public ArrayList<ArrayList<Integer>> getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Gets the logical state of the enemy's board.
     *
     * @return The enemy's board as a 2D ArrayList.
     */
    public ArrayList<ArrayList<Integer>> getEnemyBoard() {
        return enemyBoard;
    }

    /**
     * Gets the number of cells occupied by the player's ships.
     *
     * @return The player's remaining ship cell count.
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Gets the number of cells occupied by the enemy's ships.
     *
     * @return The enemy's remaining ship cell count.
     */
    public int getEnemyCount() {
        return enemyCount;
    }

    /**
     * Checks if it is the enemy's turn.
     *
     * @return True if it is the enemy's turn, false otherwise.
     */
    public boolean isEnemyTurn() {
        return isEnemyTurn;
    }

    /**
     * Checks if the game has ended.
     *
     * @return True if the game has ended, false otherwise.
     */
    public boolean isGameEnded() {
        return endGame;
    }

    /**
     * Prints the current state of a board.
     *
     * @param board The board to print, represented as a 2D ArrayList.
     */
    public void printBoard(ArrayList<ArrayList<Integer>> board) {
        if (board == null || board.isEmpty()) {
            System.out.println("Board is empty or not initialized.");
            return;
        }

        for (ArrayList<Integer> row : board) {
            for (Integer cell : row) {
                System.out.print(cell + "\t"); // Prints each cell followed by a tab for spacing.
            }
            System.out.println(); // New line after each row.
        }
    }
}
