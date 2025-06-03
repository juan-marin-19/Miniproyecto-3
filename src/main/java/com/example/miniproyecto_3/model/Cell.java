package com.example.miniproyecto_3.model;

import java.io.Serializable;

public class Cell implements Serializable {
    private final int row;
    private final int col;

    private boolean isHit = false;
    private Ship occupyingShip = null;

    // Constructor
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean isOccupied() {
        return occupyingShip != null;
    }

    public boolean isHit() {
        return isHit;
    }

    public Ship getShip() {
        return occupyingShip;
    }

    // Acciones
    public void placeShip(Ship ship) {
        this.occupyingShip = ship;
    }

    public void hit() {
        this.isHit = true;
        if (occupyingShip != null) {
            occupyingShip.registerHit();
        }
    }
}