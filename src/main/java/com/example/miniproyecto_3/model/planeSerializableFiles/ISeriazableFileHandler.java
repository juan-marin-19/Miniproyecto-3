package com.example.miniproyecto_3.model.planeSerializableFiles;

public interface ISeriazableFileHandler {
    void serialize(String fileName, Object element);
    Object deserialize(String fileName);
}
