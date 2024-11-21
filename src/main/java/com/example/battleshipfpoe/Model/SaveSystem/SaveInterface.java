package com.example.battleshipfpoe.Model.SaveSystem;

import java.io.IOException;

public interface SaveInterface<T> {
        void save(T data, String filePath) throws IOException;
        T load(String filePath) throws IOException, ClassNotFoundException;
        boolean fileExists(String filePath);
}

