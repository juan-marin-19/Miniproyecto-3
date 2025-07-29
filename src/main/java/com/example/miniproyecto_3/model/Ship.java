package com.example.miniproyecto_3.model;

import java.io.Serializable;


/**
 * Class that represents a ship. Each ship has a size, orientation, a number of remaining lives,
 * and some helper auxiliar variables for game logic and visuals.
 *
 */
public class Ship implements Serializable, IShip {

    /**
     * Orientation of the ship: true for horizontal, false for vertical.
     * @serial true = horizontal, false = vertical
     */
    private final boolean orientation;

    /**
     * Number of cells the ship occupies.
     * @serial Must be > 0
     */
    private final int size;

    /**
     * Auxiliary boolean used for visual tracking in GameController.
     * @serial true = drawn, false = not yet drawn
     */
    private boolean aux;

    /**
     * Number of remaining lives (not hit parts) of the ship
     * @serial Must be >= 0
     */
    private int lives;

    /**
     * Indicates if the ship has already been counted as sunken.
     * @serial true if already counted, false otherwise
     */
    private boolean alreadyCounted = false;



    /**
     * Creates a ship with a given size and orientation.
     *
     * @param size number of cells the ship occupies
     * @param orientation true if horizontal, false if vertical
     */
    public Ship(int size, boolean orientation) {
        this.size = size;
        this.orientation = orientation;
        this.lives = size;
    }


    /**
     * @return the size of the ship
     */
    @Override
    public int getSize() { return size; }


    /**
     * @return the orientation of the ship (true = horizontal, false = vertical)
     */
    @Override
    public boolean getOrientation() { return orientation; }


    /**
     * @return the number of remaining lives (parts that haven’t been hit yet)
     */
    @Override
    public int getLives() {return lives; }


    /**
     * @return the visual helper aux used in GameController
     */
    @Override
    public boolean getAux() { return aux; }


    /**
     * Sets the visual helper aux used in GameController.
     *
     * @param aux true if the ship has already been drawn
     */
    @Override
    public void setAux(boolean aux) { this.aux = aux; }


    /**
     * Registers a hit on the ship, decreasing its remaining lives by one.
     */
    @Override
    public void registerHit(){
        this.lives--;
    }


    /**
     * @return true if the ship has already been counted as sunken
     */
    @Override
    public boolean isAlreadyCounted() {
        return alreadyCounted;
    }


    /**
     * Sets whether the ship has already been counted as sunken.
     *
     * @param alreadyCounted true if already counted
     */
    @Override
    public void setAlreadyCounted(boolean alreadyCounted) {
        this.alreadyCounted = alreadyCounted;
    }



}
