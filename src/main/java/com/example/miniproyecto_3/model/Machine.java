package com.example.miniproyecto_3.model;

import java.io.Serializable;


import java.io.Serializable;
import java.util.List;
import java.util.Random;


public class Machine implements Serializable {

    private Board board;
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

    /**
     * Lleno el tablero de los barcos correctos
     * */
    public void fillBoard(){
        int[] shipLengths = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // Puedes ajustar las longitudes aquí
        Random random = new Random();

        for (int length : shipLengths) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10) + 1; // entre 1 y 10
                int col = random.nextInt(10) + 1;
                boolean isVertical = random.nextBoolean();

                if (board.canPlaceShip(row, col, length, isVertical)) {
                    List<int[]> coords = board.getCoordinatesForShip(row, col, length, isVertical);
                    board.placeShip(coords, !isVertical); // isHorizontal = !isVertical
                    placed = true;
                }
            }
        }
    }


    public void setBoard(Board board) {
        this.board = board;
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
