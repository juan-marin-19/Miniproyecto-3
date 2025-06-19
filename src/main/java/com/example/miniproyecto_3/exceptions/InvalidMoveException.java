package com.example.miniproyecto_3.exceptions;
//esta excepción se lanza cuando el jugador intenta disparar en una celda que ya fue golpeada.
public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message){
        super(message);
    }
}
