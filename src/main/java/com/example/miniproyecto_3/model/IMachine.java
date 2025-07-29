package com.example.miniproyecto_3.model;
/**
 * Interface representing a Machine player
 */

public interface IMachine {
    /**
     * Logic for the machine to shoot randomly on the player's board.
     *
     * @return the player's board after the machine's move
     */
    public Board makeMove(Board playerBoard);


    /**
     * Fills the machines board with the correct ships.
     */
    public void fillBoard();


    /**
     * @return machines board
     */
    public Board getBoard();


    /**
     * @param board the board to set for the machine
     */
    public void setBoard(Board board);




}
