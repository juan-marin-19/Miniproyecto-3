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

    //Clase interna con constantes de resultado
    public static class Result {
        public static final String AGUA  = "AGUA";
        public static final String TOCADO = "TOCADO";
        public static final String HUNDIDO = "HUNDIDO";
    }

    public String ResultShot(int row, int column) {
        Cell c = board.get(row).get(column);
        if(c.isFull()) {
            return "Ya disparaste aquí";
        }
        c.setShot();
        if(c.getShip() != null) {
            Ship Shotship = c.getShip();
            Shotship.setCellsTouched(c);
            return Shotship.isSunkenShip() ? Result.HUNDIDO : Result.TOCADO;
        }
        return Result.AGUA;
    }

    public boolean allTheSunkenShips(){
        for(Ship ship : ships) {
            if(!ship.isSunkenShip()) {
                return false;
            }
        } return true;
    }

    public Cell getCell(int row, int column) {
        return board.get(row).get(column);
    }

    public List<Ship> getShips() {
        return ships;
    }

}