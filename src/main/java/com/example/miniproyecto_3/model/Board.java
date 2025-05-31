package com.example.miniproyecto_3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private Cell[][] cellGrid = new Cell[10][10];

    private int rows;
    private int cols;
    private final boolean[][] occupied;

    private List<Ship> ships;


    /**
     * Constructor del tablero. Inicializa una matriz 10x10 de objetos celdas.
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.occupied = new boolean[rows][cols];
        // inicialmente vacío

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                cellGrid[r][c] = new Cell(r, c);
            }
        }

    }



    /**
     * Coloco un barco en sus posiciones en el arreglo de celdas
     * */
    public void placeShip(Ship ship, List<int[]> coords) {
        for (int[] pos : coords) {
            int row = pos[0];
            int col = pos[1];
            cellGrid[row][col].placeShip(ship); // cada celda conoce qué barco está ahí
        }

    }

    /**
     * Método para saber si el barco se puede colocar es decir esta dentro de los limites y no se superpone con otro barco
     *
     * */
    public boolean canPlaceShip(int startRow, int startCol, int length, boolean isVertical) {

        return (isVertical && startRow + length - 1 <= 10) || (!isVertical && startCol + length - 1 <= 10);

    }


    /***
     *
     * obtengo las coordenadas de los cuadros que va a ocupar el barco, y devuelvo un array 2d con i como un arreglo de int y
     * es de la fila y la columna a colorear
     */
    public List<int[]> getCoordinatesForShip(int startRow, int startCol, int length, boolean isVertical) {
        List<int[]> coords = new ArrayList<>();
        if (!canPlaceShip(startRow, startCol, length, isVertical)) {
            return coords; // vacío si no se puede
        }
        for (int i = 0; i < length; i++) {
            int r = isVertical ? startRow + i : startRow;
            int c = isVertical ? startCol : startCol + i;
            coords.add(new int[]{r, c});
        }
        return coords;
    }

    public void placeShip(List<int[]> coords) {
        for (int[] pos : coords) {
            occupied[pos[0]][pos[1]] = true;
        }
    }


    public Cell getCell(int row, int col) {
        return cellGrid[row][col];
    }

    public Cell[][] getGrid() {
        return  cellGrid;
    }


}