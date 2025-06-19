package com.example.miniproyecto_3.exceptions;

public class DataLoadException extends Exception {
    public DataLoadException(String message) {
        super(message);
    }
    public DataLoadException(String message, Throwable cause){
      super(message, cause);
    }
}
