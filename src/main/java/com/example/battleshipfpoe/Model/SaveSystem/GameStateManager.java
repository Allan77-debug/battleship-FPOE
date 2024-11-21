package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

public class GameStateManager {

    private final SaveSystem<GameProgress> saveSystem;

    public GameStateManager(SaveSystem<GameProgress> saveSystem) {
        this.saveSystem = saveSystem;
    }

    public void saveGame(GameProgress gameProgress, String saveFilePath) {
        try {
            // Check that GameProgress has the correct data
            System.out.println("Saving Game: " + gameProgress);

            saveSystem.save(gameProgress, saveFilePath);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public GameProgress loadGame(String saveFilePath) {
        try {
            if (saveSystem.fileExists(saveFilePath)) {
                GameProgress loadedProgress = saveSystem.load(saveFilePath);
                System.out.println("Loaded Game: " + loadedProgress);
                return loadedProgress;
            } else {
                System.out.println("No save file found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if loading fails
    }
}

