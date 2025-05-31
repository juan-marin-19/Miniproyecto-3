package com.example.miniproyecto_3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship {



    /**
     *la clase barco tiene una lista de celdas asociadas las cuales a su vez guardan su posicion en el arreglo2d board
     *
     * tiene la orientacion 0 = horizontal, 1 = vertical
     *
     * y el tamaño del barco
     * */
    private int orientation;
    private int size;

    private List<Cell> cells; // donde está el barco

    public Ship(int size, int orientation) {
        this.size = size;
        this.orientation = orientation;
        this.cells = new ArrayList<>();
    }

    public int getSize() { return size; }
    public int getOrientation() { return orientation; }

    // añade una celda a un barco
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Verifica si el barco está completamente hundido (todas sus celdas han sido disparadas).
     */


    public void registerHit(){

    }
}
