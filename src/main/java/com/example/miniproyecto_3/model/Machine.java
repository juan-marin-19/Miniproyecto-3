package com.example.miniproyecto_3.model;

import java.io.Serializable;


import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Class to manage the machine's behavior.
 */
public class Machine implements Serializable {

    private Board board;


    /**
     * Creates the machines board.
     */
    public Machine() {
        board = new Board(10,10);

    }


    /**
     * Logic for the machine to shoot randomly on the player's board.
     *
     * @return the player's board after the machine's move
     */
    public Board makeMove(Board playerBoard) {

            boolean shot= false;

            int row = -1;
            int col = -1;

            while (!shot) {

                Random random = new Random();
                row = random.nextInt(10); // between 0 and 9
                col = random.nextInt(10);
                if(!playerBoard.getCell(row,col).isHit()){
                    playerBoard.getCell(row,col).hit();
                    shot=true;
                }

            }

            playerBoard.setLastShotRow(row);
            playerBoard.setLastShotColumn(col);

            return playerBoard;
    }


    /**
     * Fills the machines board with the correct ships.
     */
    public void fillBoard(){
        int[] shipLengths = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        Random random = new Random();

        for (int length : shipLengths) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10) + 1; // between 1 and 10
                int col = random.nextInt(10) + 1;
                boolean isVertical = random.nextBoolean();

                if (board.canPlaceShip(row, col, length, isVertical)) {
                    List<int[]> coords = board.getCoordinatesForShip(row, col, length, isVertical);
                    board.placeShip(coords, !isVertical);
                    placed = true;
                }
            }
        }
    }


    /**
     * @return machines board
     */
    public Board getBoard() {
        return board;
    }


    /**
     * @param board the board to set for the machine
     */
    public void setBoard(Board board) {
        this.board = board;
    }




}
