package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.*;
import java.util.stream.Collectors;

/**
 * Handles saving and loading plain text data to and from files.
 * Implements the {@link SaveInterface} for handling text-based persistence.
 */
public class PlainTextSaveHandler implements SaveInterface<String> {

    /**
     * Saves the given data as plain text to the specified file path.
     *
     * @param data     The data to be saved as plain text.
     * @param filePath The file path where the data will be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void save(String data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data);
        }
    }

    /**
     * Loads plain text data from the specified file path.
     *
     * @param filePath The file path to load the data from.
     * @return The loaded data as a {@link String}.
     * @throws IOException If the file does not exist or an error occurs while reading.
     */
    @Override
    public String load(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Save file not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Checks if a file exists at the specified file path.
     *
     * @param filePath The file path to check for existence.
     * @return True if the file exists, false otherwise.
     */
    @Override
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
