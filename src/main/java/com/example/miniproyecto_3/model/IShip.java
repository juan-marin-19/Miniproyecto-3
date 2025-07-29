package com.example.miniproyecto_3.model;

public interface IShip {

    /**
     * @return the size of the ship
     */
    int getSize();


    /**
     * @return the orientation of the ship (true = horizontal, false = vertical)
     */
    boolean getOrientation();


    /**
     * @return the number of remaining lives (parts that haven’t been hit yet)
     */
    int getLives();


    /**
     * @return the visual helper aux used in GameController
     */
    boolean getAux();


    /**
     * Sets the visual helper aux used in GameController.
     *
     * @param aux true if the ship has already been drawn
     */
    void setAux(boolean aux);


    /**
     * Registers a hit on the ship, decreasing its remaining lives by one.
     */
    void registerHit();


    /**
     * @return true if the ship has already been counted as sunken
     */
    public boolean isAlreadyCounted();


    /**
     * Sets whether the ship has already been counted as sunken.
     *
     * @param alreadyCounted true if already counted
     */
    void setAlreadyCounted(boolean alreadyCounted);

}
