package com.example.miniproyecto_3.model.planeSerializableFiles;

/**
 * Interface that defines methods for saving and loading serializable objects.
 */
public interface ISeriazableFileHandler {

    /**
     * Saves a serializable object to a file.
     *
     * @param fileName the name of the file
     * @param element the object to serialize
     */
    void serialize(String fileName, Object element);

    /**
     * Loads a serializable object from a file.
     *
     * @param fileName the name of the file
     * @return the deserialized object, or null if there is an error
     */
    Object deserialize(String fileName);
}
