package com.example.miniproyecto_3.exceptions;
//esta excepción se lanza si ocurre un problema al cargar los archivos .csv y .ser
public class DataLoadException extends Exception {
    public DataLoadException(String message) {
        super(message);
    }
    public DataLoadException(String message, Throwable cause){
      super(message, cause);
    }
}
