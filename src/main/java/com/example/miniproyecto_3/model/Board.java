package com.example.miniproyecto_3.model;

import java.util.List;

public class Board {
    private final int rows = 10;
    private final int columns = 10;
    private List<List<Cell>> board;
    private List<Ship> ships;
}
