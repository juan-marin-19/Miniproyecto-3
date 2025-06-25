package com.example.miniproyecto_3.model.planeSerializableFiles;

import java.io.*;

/**
 * Class that handles reading and writing of serializable objects to and from files.
 */
public class SeriazableFileHandler implements ISeriazableFileHandler {

    /**
     * Empty constructor.
     */
    public void SeriazableFileHandler() {}


    /**
     * Serializes an object and saves it in a file.
     *
     * @param fileName the name of the file to write
     * @param element the object to serialize
     */
    @Override
    public void serialize(String fileName, Object element) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(element);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Reads a serialized object from a file and returns it.
     *
     * @param fileName the name of the file to read
     * @return the deserialized object, or null if there is an error
     */
    @Override
    public Object deserialize(String fileName) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
