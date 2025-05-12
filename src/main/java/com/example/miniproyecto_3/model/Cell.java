package com.example.miniproyecto_3.model;

import java.io.Serializable;

public class Cell {
    private int row; //fila de la celda
    private int col; // columna de la celda
    private boolean shot;  // si la celda fue disparada
    private Ship ship; // Barco asignado a esta celda (puede ser null)


    /**
     *
     * celda representa una posicion del tablero
     * */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.shot = false;
        this.ship = null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    /**
     * indica si la celda tiene o no un barco
     */
    public boolean hasShip() {
        return ship != null;
    }

    /**
     * Marca esta celda como disparada.
     */
    public void markShot() {
        this.shot = true;
    }

    /**
     * Verifica si la celda fue disparada.
     *
     * @return true si fue disparada, false si no.
     */
    public boolean wasShot() {
        return shot;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}