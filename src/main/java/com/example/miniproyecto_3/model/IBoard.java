package com.example.miniproyecto_3.model;

import java.util.List;
/**
 * Interface representing the board functionality.
 */
public interface IBoard {
    /**
     * Method to check if a ship can be placed.
     *
     * @param startRow row of the top or leftmost cell of the ship
     * @param startCol column of the top or leftmost cell of the ship
     * @param length number of cells the ship occupies
     * @param isVertical ship orientation
     *
     * @return true if the ship can be placed, false otherwise
     */
    boolean canPlaceShip(int startRow, int startCol, int length, boolean isVertical);

    /**
     * Gets the coordinates of the cells that a ship will occupy, returns a list of int arrays where each array has two elements:
     * row and column of a cell occupied by the ship.
     *
     * @param startRow row of the top or leftmost cell of the ship
     * @param startCol column of the top or leftmost cell of the ship
     * @param length number of cells the ship occupies
     * @param isVertical ship orientation
     *
     * @return list of int arrays with the coordinates of the ship
     */
    List<int[]> getCoordinatesForShip(int startRow, int startCol, int length, boolean isVertical);

    /**
     * Places the same Ship object in the given cells if the validation is already done.
     *
     * @param coords coordinates of the cells where the ship will be placed
     * @param isHorizontal ship orientation
     */
    void placeShip(List<int[]> coords, boolean isHorizontal);

    /**
     * Prints the current board with cell information.
     */
    void printCellGrid();

    /**
     * Returns a Cell from the cell array
     *
     * @param row row of the cell to return
     * @param col column of the cell to return
     *
     * @return Cell object that contains a ship or is empty
     */
    Cell getCell(int row, int col);


    /**
     * Saves the row of the last cell that machine shot.
     *
     * @param lastShotRow row of the last shot
     */
    void setLastShotRow(int lastShotRow);

    /**
     * Saves the column of the last cell that machine shot.
     *
     * @param lastShotColumn column of the last shot
     */
    void setLastShotColumn(int lastShotColumn);


    /**
     * @return the row of the last cell shot by machine
     */
    int getLastShotRow();


    /**
     * @return the column of the last cell shot by machine
     */
    int getLastShotColumn();

}
