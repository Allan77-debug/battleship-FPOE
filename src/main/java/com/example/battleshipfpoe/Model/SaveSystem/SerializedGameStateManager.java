package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

/**
 * Manages the game state using serialized files.
 */
public class SerializedGameStateManager {

    private final SaveSystem<GameProgress> saveSystem;

    /**
     * Constructs a new SerializedGameStateManager with the specified save system.
     *
     * @param saveSystem The save system to use for saving and loading game data.
     */
    public SerializedGameStateManager(SaveSystem<GameProgress> saveSystem) {
        this.saveSystem = saveSystem;
    }

    /**
     * Saves the game progress to a specified file path.
     *
     * @param gameProgress The game progress to save.
     * @param saveFilePath The file path where the game progress will be saved.
     */
    public void saveGame(GameProgress gameProgress, String saveFilePath) {
        try {
            saveSystem.save(gameProgress, saveFilePath);
            System.out.println("Game saved successfully (Serialized)!");
        } catch (IOException e) {
            System.err.println("Error saving game (Serialized): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the game progress from a specified file path.
     *
     * @param saveFilePath The file path from which to load the game progress.
     * @return The loaded game progress, or null if loading fails.
     */
    public GameProgress loadGame(String saveFilePath) {
        try {
            if (saveSystem.fileExists(saveFilePath)) {
                GameProgress loadedProgress = saveSystem.load(saveFilePath);
                System.out.println("Game loaded successfully (Serialized): " + loadedProgress);
                return loadedProgress;
            } else {
                System.out.println("No save file found (Serialized).");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game (Serialized): " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if loading fails
    }
}