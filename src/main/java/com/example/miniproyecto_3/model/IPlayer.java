package com.example.miniproyecto_3.model;
/**
 * Interface representing a player
 */
public interface IPlayer {
    /**
     * @return the nickname of the player
     */
    String getNickname();

    /**
     * @return the number of ships sunk by the player
     */
    int getSunkenShips();

    /**
     * Sets the number of sunken ships for the player.
     *
     * @param sunkenShips the new number of sunken ships
     */
    void setSunkenShips(int sunkenShips);


    /**
     * @return a string with the player's information
     */

    public String toString();

}
