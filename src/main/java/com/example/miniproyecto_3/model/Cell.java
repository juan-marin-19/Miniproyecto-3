package com.example.miniproyecto_3.model;

public class Cell {
    private int row;
    private int column;
    private boolean shot  = false;
    private Ship ship = null;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public boolean isShot() {
        return shot;
    }
    public void setShot(){
        this.shot = true;
    }
    public Ship getShip() {
        return ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    public boolean isFull(){
        if(ship != null){
            return true;
        }
        return false;
    }
}