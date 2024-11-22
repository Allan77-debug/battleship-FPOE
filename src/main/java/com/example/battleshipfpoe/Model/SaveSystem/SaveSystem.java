package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

/**
 * A generic save system that delegates saving, loading, and file existence checks
 * to a specified {@link SaveInterface} implementation.
 *
 * @param <T> The type of data to be saved and loaded.
 */
public class SaveSystem<T> {

    private final SaveInterface<T> saveHandler; // The handler used to save and load data.

    /**
     * Constructs a new SaveSystem with the specified save handler.
     *
     * @param saveHandler The {@link SaveInterface} implementation to handle saving and loading.
     */
    public SaveSystem(SaveInterface<T> saveHandler) {
        this.saveHandler = saveHandler;
    }

    /**
     * Saves the specified data to the given file path.
     *
     * @param data     The data to save.
     * @param filePath The file path where the data will be saved.
     * @throws IOException If an error occurs while saving the data.
     */
    public void save(T data, String filePath) throws IOException {
        saveHandler.save(data, filePath);
    }

    /**
     * Loads data from the specified file path.
     *
     * @param filePath The file path from which to load the data.
     * @return The loaded data of type {@code T}.
     * @throws IOException            If an error occurs while loading the data.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public T load(String filePath) throws IOException, ClassNotFoundException {
        return saveHandler.load(filePath);
    }

    /**
     * Checks if a file exists at the specified file path.
     *
     * @param filePath The file path to check.
     * @return True if the file exists, false otherwise.
     */
    public boolean fileExists(String filePath) {
        return saveHandler.fileExists(filePath);
    }
}
