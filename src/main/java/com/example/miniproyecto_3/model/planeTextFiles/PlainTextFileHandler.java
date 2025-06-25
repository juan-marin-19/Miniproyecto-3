package com.example.miniproyecto_3.model.planeTextFiles;

import java.io.*;


/**
 * Class that handles reading and writing plain text files.
 * It writes a single line of content and reads data separated by commas.
 */
public class PlainTextFileHandler implements IPlainTextFileHandler {


    /**
     * Writes content to a plain text file.
     *
     * @param fileName the name of the file to write
     * @param content the content to write in the file
     */
    @Override
    public void writeToFile(String fileName, String content) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Reads content from a plain text file.
     * Returns the data split by commas.
     *
     * @param fileName the name of the file to read
     * @return an array of strings with the data read from the file
     */
    @Override
    public String[] readFromFile(String fileName) {
            StringBuilder content = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                String line;
                while((line = reader.readLine()) != null){
                    content.append(line.trim()).append(",");
                }
            }catch (IOException e) {
                e.printStackTrace();
                }
            return content.toString().split(",");
        }


}
