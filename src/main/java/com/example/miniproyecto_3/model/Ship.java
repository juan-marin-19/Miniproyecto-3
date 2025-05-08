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
}
