package com.example.miniproyecto_3.model.planeTextFiles;

/**
 * Interface that defines methods for writing and reading plain text files.
 */
public interface IPlainTextFileHandler {

    /**
     * Writes content to a plain text file.
     *
     * @param fileName the name of the file
     * @param content the text content to write
     */
    void writeToFile(String fileName, String content);

    /**
     * Reads content from a plain text file and returns it as an array of strings.
     *
     * @param fileName the name of the file
     * @return the file content split into parts
     */
    String[] readFromFile(String fileName);
}
