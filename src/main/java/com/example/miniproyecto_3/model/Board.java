package com.example.miniproyecto_3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows = 10;
    private final int cols = 10;
    private Cell[][] grid;

    private List<Ship> ships;


    /**
     * Constructor del tablero. Inicializa una matriz 10x10 de objetos celdas.
     */
    public Board() {
        grid = new Cell[rows][cols];
        ships = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Coloca un barco en el tablero si el espacio está libre.
     * @param ship El barco a colocar.
     * @param startRow Fila inicial.
     * @param startCol Columna inicial.
     * @return true si se colocó correctamente, false si había conflicto o salía del tablero.
     */

    public boolean placeShip(Ship ship, int startRow, int startCol) {
        int size = ship.getSize();
        int orientation = ship.getOrientation();



        List<Cell> positions = new ArrayList<>();


        //Calcula la posición de cada celda que ocupará el barco
        for (int i = 0; i < size; i++) {
            int row = startRow + (orientation == 1 ? i : 0);
            int col = startCol + (orientation == 0 ? i : 0);

            if (row >= rows || col >= cols || grid[row][col].hasShip()) {
                return false; // fuera del tablero o solapado
            }

            positions.add(grid[row][col]);
        }

        // Asignar el barco a las celdas
        for (Cell cell : positions) {
            cell.setShip(ship);
            ship.addCell(cell);
        }

        ships.add(ship);
        return true;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Cell[][] getGrid() {
        return grid;
    }


}