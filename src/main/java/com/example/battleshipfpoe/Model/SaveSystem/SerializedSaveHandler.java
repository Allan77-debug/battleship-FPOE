package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.*;

/**
 * Handles saving and loading serialized objects to and from files.
 * Implements the {@link SaveInterface} to provide functionality for object persistence.
 *
 * @param <T> The type of object to be serialized and deserialized.
 */
public class SerializedSaveHandler<T> implements SaveInterface<T> {

    /**
     * Saves the given object to a file using Java serialization.
     *
     * @param data     The object to be saved.
     * @param filePath The file path where the object will be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void save(T data, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        }
    }

    /**
     * Loads an object from a file using Java deserialization.
     *
     * @param filePath The file path from which the object will be loaded.
     * @return The loaded object of type {@code T}.
     * @throws IOException            If the file does not exist or an error occurs while reading.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    @Override
    public T load(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Save file not found: " + filePath);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            T loadedData = (T) ois.readObject();
            System.out.println("Loaded Data: " + loadedData);
            return loadedData;
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
