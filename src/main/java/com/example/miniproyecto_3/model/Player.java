package com.example.miniproyecto_3.model;


import java.io.Serializable;

/**
 * Class that stores the player's information, including their nickname and number of sunken ships.
 */
public class Player {
        private final String nickname;
        private int sunkenShips;

    /**
     * Creates a player with a nickname and a number of sunken ships.
     *
     * @param nickname the name of the player
     * @param sunkenShips the number of ships the player has sunk
     */
    public Player(String nickname, int sunkenShips) {
            this.nickname = nickname;
            this.sunkenShips = sunkenShips;

        }


    /**
     * @return the nickname of the player
     */
    public String getNickname() {
            return nickname;
        }

    /**
     * @return the number of ships sunk by the player
     */
    public int getSunkenShips() {
            return sunkenShips;
        }

    /**
     * Sets the number of sunken ships for the player.
     *
     * @param sunkenShips the new number of sunken ships
     */
    public void setSunkenShips(int sunkenShips) {
            this.sunkenShips = sunkenShips;
        }


    /**
     * @return a string with the player's information
     */
    @Override
    public String toString() {
            return "Player{" +
                    "nickname='" + nickname + '\'' +
                    ", sunken ships=" + sunkenShips +
                    '}';
        }


}
