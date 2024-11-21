package com.example.battleshipfpoe.Model.SaveSystem;

import com.example.battleshipfpoe.Model.SaveSystem.SaveInterface;

import java.io.*;
import java.util.stream.Collectors;

public class PlainTextSaveHandler implements SaveInterface<String> {

    @Override
    public void save(String data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data);
        }
    }

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

    @Override
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}