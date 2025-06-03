package com.example.miniproyecto_3.model;

import java.io.Serializable;


import java.io.Serializable;


public class Machine implements Serializable {

    private final Board board;
    private int sunkenShips;

    public Machine( int sunkenShips) {
        board = new Board(10,10);
        this.sunkenShips = sunkenShips;
    }


    public Board getBoard() {
        return board;
    }

    public void makeMove(Board playerBoard) {
        // lógica para que la máquina dispare aleatoriamente en el board del jugador
    }

    public int getSunkenShips() {
        return sunkenShips;
    }

    public void setSunkenShips(int sunkenShips) {
        this.sunkenShips = sunkenShips;
    }

    public void incrementSunkenShips() {
        this.sunkenShips++;
    }


}
