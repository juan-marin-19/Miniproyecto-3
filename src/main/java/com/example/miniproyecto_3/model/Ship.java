package com.example.miniproyecto_3.model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {



    /**
     *la clase barco tiene una lista de celdas asociadas las cuales a su vez guardan su posición en el arreglo2d board
     *
     * tiene la orientación, verdadero = horizontal, falso = vertical
     *
     * y el tamaño del barco
     * */
    private boolean orientation;
    private int size;
    private boolean aux;
    private int lifes;

    private List<Cell> cells; // donde está el barco

    public Ship(int size, boolean orientation) {
        this.size = size;
        this.orientation = orientation;
        this.cells = new ArrayList<>();
        this.lifes = size;
    }

    public int getSize() { return size; }
    public boolean getOrientation() { return orientation; }

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


    public int getLifes() {return lifes; }
    public boolean getAux() { return aux; }
    public void setAux(boolean aux) { this.aux = aux; }

    public void registerHit(){
        this.lifes--;
    }
}
