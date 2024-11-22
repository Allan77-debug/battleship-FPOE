package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

/**
 * Manages the game state using plain text files.
 */
public class PlainTextGameStateManager {

    private final SaveSystem<String> saveSystem;

    /**
     * Constructs a new PlainTextGameStateManager with the specified save system.
     *
     * @param saveSystem The save system to use for saving and loading game data.
     */
    public PlainTextGameStateManager(SaveSystem<String> saveSystem) {
        this.saveSystem = saveSystem;
    }

    /**
     * Saves the game data to a specified file path.
     *
     * @param gameData    The game data to save.
     * @param saveFilePath The file path where the game data will be saved.
     */
    public void saveGame(String gameData, String saveFilePath) {
        try {
            saveSystem.save(gameData, saveFilePath);
            System.out.println("Game saved successfully (Plain Text)!");
        } catch (IOException e) {
            System.err.println("Error saving game (Plain Text): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the game data from a specified file path.
     *
     * @param saveFilePath The file path from which to load the game data.
     * @return The loaded game data, or null if loading fails.
     */
    public String loadGame(String saveFilePath) {
        try {
            if (saveSystem.fileExists(saveFilePath)) {
                String loadedData = saveSystem.load(saveFilePath);
                System.out.println("Game loaded successfully (Plain Text): " + loadedData);
                return loadedData;
            } else {
                System.out.println("No save file found (Plain Text).");
            }
        } catch (IOException e) {
            System.err.println("Error loading game (Plain Text): " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if loading fails
    }
}