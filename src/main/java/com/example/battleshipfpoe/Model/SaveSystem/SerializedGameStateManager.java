package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

import java.io.IOException;

public class SerializedGameStateManager {

    private final SaveSystem<GameProgress> saveSystem;

    public SerializedGameStateManager(SaveSystem<GameProgress> saveSystem) {
        this.saveSystem = saveSystem;
    }

    public void saveGame(GameProgress gameProgress, String saveFilePath) {
        try {
            saveSystem.save(gameProgress, saveFilePath);
            System.out.println("Game saved successfully (Serialized)!");
        } catch (IOException e) {
            System.err.println("Error saving game (Serialized): " + e.getMessage());
            e.printStackTrace();
        }
    }

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
