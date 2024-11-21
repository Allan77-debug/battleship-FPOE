package com.example.battleshipfpoe.Model.SaveSystem;

import com.example.battleshipfpoe.Model.SaveSystem.GameProgress;

import java.io.*;

import java.io.IOException;

public class SaveSystem<T> {

    private final SaveInterface<T> saveHandler;

    public SaveSystem(SaveInterface<T> saveHandler) {
        this.saveHandler = saveHandler;
    }

    public void save(T data, String filePath) throws IOException {
        saveHandler.save(data, filePath);
    }

    public T load(String filePath) throws IOException, ClassNotFoundException {
        return saveHandler.load(filePath);
    }

    public boolean fileExists(String filePath) {
        return saveHandler.fileExists(filePath);
    }
}
