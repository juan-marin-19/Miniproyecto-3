package com.example.miniproyecto_3.model.planeSerializableFiles;

import java.io.*;

public class SeriazableFileHandler implements ISeriazableFileHandler {

    public void SeriazableFileHandler() {}

    @Override
    public void serialize(String fileName, Object element) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(element);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
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
