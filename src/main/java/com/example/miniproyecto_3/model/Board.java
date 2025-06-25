package com.example.miniproyecto_3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to store the board where the ships are placed and manage the logic for positioning them and other related operations.
 *
 */
public class Board implements Serializable {


    /**
     * 2D array of Cell objects, each representing a space on the board.
     *
     * @serial Each cell may or may not contain a Ship.
     */
    private final Cell[][] cellGrid = new Cell[10][10];

    /**
     * 2D boolean array indicating which cells are already occupied by a ship.
     *
     * @serial true if a cell contains a ship; false otherwise.
     */
    private final boolean[][] occupied;

    /**
     * Row index of the last shot made by the machine.
     *
     * @serial Must be a value between 0 and 9.
     */
    private int lastShotRow;

    /**
     * Column index of the last shot made by the machine.
     * @serial Must be a value between 0 and 9.
     */
    private int lastShotColumn;


    /**
     * Board constructor, initializes a 10x10 array of Cell objects
     *
     * @param rows number of rows in the boolean array
     * @param cols number of columns in the boolean array
     */
    public Board(int rows, int cols) {
        this.occupied = new boolean[rows][cols];
        // inicialmente vacío

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                cellGrid[r][c] = new Cell();
            }
        }

    }


    /**
     * Method to check if a ship can be placed, that is, it fits within the board limits
     * and does not overlap with another ship.
     *
     * @param startRow row of the top or leftmost cell of the ship
     * @param startCol column of the top or leftmost cell of the ship
     * @param length number of cells the ship occupies
     * @param isVertical ship orientation
     *
     * @return true if the ship can be placed, false otherwise
     */
    public boolean canPlaceShip(int startRow, int startCol, int length, boolean isVertical) {

        startRow = startRow - 1;
        startCol = startCol - 1;

       if((isVertical && startRow + length - 1 < 10) || (!isVertical && startCol + length - 1 < 10)){
           for (int i = 0; i < length; i++) {
               int r = isVertical ? startRow + i : startRow;
               int c = isVertical ? startCol : startCol + i;

               if (occupied[r][c]) {
                   return false; // There's already a ship
               }
           }

           return true;
       }
       else{
           return false;
       }


    }


    /**
     * Gets the coordinates of the cells that a ship will occupy, returns a list of int arrays where each array has two elements:
     * row and column of a cell occupied by the ship.
     *
     * @param startRow row of the top or leftmost cell of the ship
     * @param startCol column of the top or leftmost cell of the ship
     * @param length number of cells the ship occupies
     * @param isVertical ship orientation
     *
     * @see #canPlaceShip(int, int, int, boolean)
     *
     * @return list of int arrays with the coordinates of the ship
     */
    public List<int[]> getCoordinatesForShip(int startRow, int startCol, int length, boolean isVertical) {
        List<int[]> coords = new ArrayList<>();
        if (!canPlaceShip(startRow, startCol, length, isVertical)) {
            return coords; // Empty if it can't be placed
        }
        for (int i = 0; i < length; i++) {
            int r = isVertical ? startRow + i : startRow;
            int c = isVertical ? startCol : startCol + i;
            coords.add(new int[]{r, c});
        }
        return coords;
    }

    /**
     * Places the same Ship object in the given cells if the validation is already done.
     *
     * @param coords coordinates of the cells where the ship will be placed
     * @param isHorizontal ship orientation
     *
     * @see #printCellGrid()
     */
    public void placeShip(List<int[]> coords, boolean isHorizontal) {
        Ship ship = new Ship(coords.size(), isHorizontal);
        for (int[] pos : coords) {
            // Place the same ship object in the correct coordinates that were obtained
            try {
                cellGrid[pos[0] - 1][pos[1] - 1].placeShip(ship);
                occupied[pos[0] - 1][pos[1] - 1] = true;
            } catch (ArrayIndexOutOfBoundsException e) {  // catches the exception if any index is out of the 0-9 range
                System.out.println("Error: coordinates out of board at [" + pos[0] + ";" + pos[1] + "]");
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(occupied[i][j] ? "O " : "X ");
            }
            System.out.println();
        }
        System.out.println("\n\n");

        printCellGrid();

        System.out.println("\n\n");
    }



    /**
     * Prints the current board with cell information.
     */
    public void printCellGrid() {
        for(int i =0; i < 10; i++){
            for(int j =0; j < 10; j++){
                if (occupied[i][j]) {
                    System.out.print(cellGrid[i][j].getShip().getSize() + " ");
                }else{
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }


    /**
     * Returns a Cell from the cell array
     *
     * @param row row of the cell to return
     * @param col column of the cell to return
     *
     * @return Cell object that contains a ship or is empty
     */
    public Cell getCell(int row, int col) {
        return cellGrid[row][col];
    }


    /**
     * Saves the row of the last cell that machine shot.
     *
     * @param lastShotRow row of the last shot
     */
    public void setLastShotRow(int lastShotRow) {this.lastShotRow = lastShotRow;}


    /**
     * Saves the column of the last cell that machine shot.
     *
     * @param lastShotColumn column of the last shot
     */
    public void setLastShotColumn(int lastShotColumn) {this.lastShotColumn = lastShotColumn;}


    /**
     * @return the row of the last cell shot by machine
     */
    public int getLastShotRow(){return lastShotRow;}


    /**
     * @return the column of the last cell shot by machine
     */
    public int getLastShotColumn(){return lastShotColumn;}




}