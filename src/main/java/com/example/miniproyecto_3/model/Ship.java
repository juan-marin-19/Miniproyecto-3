package com.example.miniproyecto_3.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private String type;
    private int size;
    private List<Cell> locations;
    private List<Cell> cellsTouched;

    public Ship(String type, int size){
        this.type = type;
        this.size = size;
        this.locations = new ArrayList<>();
        this.cellsTouched = new ArrayList<>();
    }

    public String getType() {
        return type;
    }
    public int getSize() {
        return size;
    }
    public void addLocation(Cell location){
        locations.add(location);
    }
    public List<Cell> getLocations() {
        return locations;
    }
    public List<Cell> getCellsTouched() {
        return cellsTouched;
    }
    public void setCellsTouched(Cell cellTouched) {
        if(!cellsTouched.contains(cellTouched)){
            cellsTouched.add(cellTouched);
        }
    }
    public boolean containsCell(Cell cell){
        return cellsTouched.contains(cell);
    }
    public boolean isSunkenShip(){
        return cellsTouched.size() == size;
    }
}
