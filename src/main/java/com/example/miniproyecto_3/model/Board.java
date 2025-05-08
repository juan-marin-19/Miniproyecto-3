package com.example.miniproyecto_3.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows = 10;
    private final int columns = 10;
    private List<List<Cell>> board;
    private List<Ship> ships;

    public Board() {
        board = new ArrayList<>();
        ships = new ArrayList<>();

        //initialize the list of list
        for (int i = 0; i < rows; i++) {
            List<Cell> rowCells = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                rowCells.add(new Cell(i, j));
            }
            board.add(rowCells);
        }
    }

    public boolean setShip(Ship ship, int initialRow, int initialColumn, boolean isHorizontal) {
        List<Cell> positions = new ArrayList<>();
        for (int i = 0; i < ship.getSize(); i++) {
            int row, column = 0;
            if (isHorizontal) {
                row = initialRow;
                column = initialColumn + i;
            } else {
                row = initialRow + i;
                column = initialColumn;
            }
            if (row >= rows && column >= columns) {
                return false;
            }

            Cell finalCell = board.get(row).get(column);
            if (finalCell.isFull()) {
                return false;
            }

            positions.add(finalCell);
        }
        for (Cell c : positions) {
            c.setShip(ship);
            ship.addLocation(c);
        }
        ships.add(ship);
        return true;
    }
}