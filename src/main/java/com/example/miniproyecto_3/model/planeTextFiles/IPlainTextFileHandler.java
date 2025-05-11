package com.example.miniproyecto_3.model.planeTextFiles;


public interface IPlainTextFileHandler {
        void writeToFile(String fileName, String content);
        String[] readFromFile(String fileName);
    }


