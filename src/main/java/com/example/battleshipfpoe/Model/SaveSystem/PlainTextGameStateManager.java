package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

public class PlainTextGameStateManager {

    private final SaveSystem<String> saveSystem;

    public PlainTextGameStateManager(SaveSystem<String> saveSystem) {
        this.saveSystem = saveSystem;
    }

    public void saveGame(String gameData, String saveFilePath) {
        try {
            saveSystem.save(gameData, saveFilePath);
            System.out.println("Game saved successfully (Plain Text)!");
        } catch (IOException e) {
            System.err.println("Error saving game (Plain Text): " + e.getMessage());
            e.printStackTrace();
        }
    }

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
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if loading fails
    }
}

