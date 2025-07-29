package com.example.miniproyecto_3.model;

import java.io.Serializable;

/**
 * Class that models a cell in the grid where a ship can be placed.
 */
public class Cell implements Serializable {

    /**
     * Indicates whether the cell has already been shot
     *
     * @serial true if hit; false otherwise.
     */
    private boolean isHit = false;


    /**
     * The ship occupying this cell, null if it's empty.
     *
     * @serial Holds a reference to a Ship object or null.
     */
    private Ship occupyingShip = null;


    /**
     * Empty constructor.
     * */
    public Cell() {

    }


    /**
     * @return true if there is a ship in the cell, false otherwise.
     */
    public boolean isOccupied() {
        return occupyingShip != null;
    }


    /**
     * @return true if the cell has been shot, false otherwise.
     */
    public boolean isHit() {
        return isHit;
    }


    /**
     * @return the ship occupying the cell, or null if there is none.
     */
    public Ship getShip() {
        return occupyingShip;
    }


    /**
     * Places a ship in the cell.
     *
     * @param ship a ship object
     */
    public void placeShip(Ship ship) {
        this.occupyingShip = ship;
    }


    /**
     * Registers a shot in the cell and also on the ship if there is one.
     */
    public void hit() {
        this.isHit = true;
        if (occupyingShip != null) {
            occupyingShip.registerHit();
        }
    }
}