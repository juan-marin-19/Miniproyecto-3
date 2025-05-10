package com.example.miniproyecto_3.model.planeTextFiles;

public interface IPlainTextFileReader {

    void writeToFile(String fileName, String content);
    String[] readFromFile(String fileName);
}
