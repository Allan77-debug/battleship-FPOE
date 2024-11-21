package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.*;

public class SerializedSaveHandler<T> implements SaveInterface<T> {

    @Override
    public void save(T data, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        }
    }

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

    @Override
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}