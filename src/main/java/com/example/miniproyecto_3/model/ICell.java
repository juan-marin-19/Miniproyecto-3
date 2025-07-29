package com.example.miniproyecto_3.model;
/**
 * Interface representing a cell of the board
 */
public interface ICell {

    /**
     * @return true if there is a ship in the cell, false otherwise.
     */
    boolean isOccupied();


    /**
     * @return true if the cell has been shot, false otherwise.
     */
    boolean isHit();


    /**
     * @return the ship occupying the cell, or null if there is none.
     */
    Ship getShip();


    /**
     * Places a ship in the cell.
     *
     * @param ship a ship object
     */
    void placeShip(Ship ship);


    /**
     * Registers a shot in the cell and also on the ship if there is one.
     */
    void hit();
}
