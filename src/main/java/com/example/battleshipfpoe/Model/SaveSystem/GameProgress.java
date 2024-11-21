package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.Serializable;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.List.ArrayList;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private final BoardHandler playerBoardHandler;
    private final BoardHandler enemyBoardHandler;
    private ArrayList<ArrayList<Integer>> playerBoard;
    private ArrayList<ArrayList<Integer>> enemyBoard;
    private final int playerCount;
    private final int enemyCount;
    private final boolean isEnemyTurn;
    private final boolean endGame;

    public GameProgress(BoardHandler playerBoardHandler, BoardHandler enemyBoardHandler, ArrayList<ArrayList<Integer>> playerBoard, ArrayList<ArrayList<Integer>> enemyBoard, int playerCount, int enemyCount, boolean isEnemyTurn, boolean endGame) {
        this.playerBoardHandler = playerBoardHandler;
        this.enemyBoardHandler = enemyBoardHandler;
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.playerCount = playerCount;
        this.enemyCount = enemyCount;
        this.isEnemyTurn = isEnemyTurn;
        this.endGame = endGame;
    }

    public BoardHandler getPlayerBoardHandler() {
        return playerBoardHandler;
    }

    public BoardHandler getEnemyBoardHandler() {
        return enemyBoardHandler;
    }
    public ArrayList<ArrayList<Integer>> getPlayerBoard() {
        return playerBoard;
    }
    public ArrayList<ArrayList<Integer>> getEnemyBoard() {
        return enemyBoard;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public boolean isEnemyTurn() {
        return isEnemyTurn;
    }


    public boolean isGameEnded() {
        return endGame;
    }

    public void printBoard(ArrayList<ArrayList<Integer>> board) {
        if (board == null || board.isEmpty()) {
            System.out.println("Board is empty or not initialized.");
            return;
        }

        for (ArrayList<Integer> row : board) {
            for (Integer cell : row) {
                System.out.print(cell + "\t");  // Prints each cell followed by a tab for spacing.
            }
            System.out.println();  // New line after each row.
        }
    }

}
