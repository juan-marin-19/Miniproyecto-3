package com.example.miniproyecto_3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Board implements Serializable {


    private Cell[][] cellGrid = new Cell[10][10];

    private int rows;
    private int cols;
    private boolean[][] occupied;
    private int lastShotRow;
    private int lastShotColumn;

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
     * Método para saber si el barco se puede colocar es decir esta dentro de los limites y no se superpone con otro barco
     *
     * */
    public boolean canPlaceShip(int startRow, int startCol, int length, boolean isVertical) {

        startRow = startRow - 1;
        startCol = startCol - 1;

       if((isVertical && startRow + length - 1 < 10) || (!isVertical && startCol + length - 1 < 10)){
           for (int i = 0; i < length; i++) {
               int r = isVertical ? startRow + i : startRow;
               int c = isVertical ? startCol : startCol + i;

               if (occupied[r][c]) {
                   return false; // ya hay un barco ahí
               }
           }

           return true;
       }
       else{
           return false;
       }


    }


    /***
     * Obtengo las coordenadas de los cuadros que va a ocupar el barco, y devuelvo un array 2d con i como un arreglo de int y
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


    /**
     * Coloco un mismo objeto barco en las celdas indicadas (ya se hizo la validación), (isHorizontal = rotated)
     * */
    public void placeShip(List<int[]> coords, boolean isHorizontal) {
        Ship ship = new Ship(coords.size(),isHorizontal);
        for (int[] pos : coords) {
            //posiciono el mismo objeto barco en las celdas coordenadas correctas que se obtuvieron
            try {
                cellGrid[pos[0] - 1][pos[1] - 1].placeShip(ship);
                occupied[pos[0] - 1][pos[1] - 1] = true;
            } catch(ArrayIndexOutOfBoundsException e) {  // atrapa la excepcion que se da cuando alguno de los indices calculados estan fuera del rango 0-9
                System.out.println("Error: coordenadas fuera del tablero en [" + pos[0] + ";" + pos[1] + "]");
            }
        }
        for(int i =0; i < 10; i++){
            for(int j =0; j < 10; j++){
                System.out.print( occupied[i][j] ? "O " : "X ") ;
            }
            System.out.println();
        }
        System.out.println("\n\n");

        printCellGrid();


    }


    public void printCellGrid() {
        for(int i =0; i < 10; i++){
            for(int j =0; j < 10; j++){
                if (occupied[i][j]) {
                    System.out.print(cellGrid[i][j].getShip().getSize() + " ");
                }else{
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }

    public Cell getCell(int row, int col) {
        return cellGrid[row][col];
    }

    public void setLastShotRow(int lastShotRow) {this.lastShotRow = lastShotRow;}
    public void setLastShotColumn(int lastShotColumn) {this.lastShotColumn = lastShotColumn;}

    public int getLastShotRow(){return lastShotRow;}
    public int getLastShotColumn(){return lastShotColumn;}

    public Cell[][] getGrid() {
        return  cellGrid;
    }


}