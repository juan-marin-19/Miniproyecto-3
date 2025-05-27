package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.Board;
import com.example.miniproyecto_3.model.Cell;
import com.example.miniproyecto_3.model.Machine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import com.example.miniproyecto_3.model.Player;
import javafx.scene.layout.Priority;

public class GameController {

    @FXML
    private GridPane playerGrid;

    @FXML
    private GridPane mainGrid;


    private Board playerBoard;
    private Board machineBoard;


    //private Player player;

    private Machine machine;

    public GameController() {


    }

    public void initialize() {
        this.playerBoard = new Board();
        this.machineBoard = new Board();
        drawPlayerGrid();
    }




    private void drawPlayerGrid() {

        // Mostrar botones o celdas con barcos

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button button = new Button();

                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                button.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightblue;");

                // button.setPrefWidth(10);   // Ancho preferido
              //  button.setPrefHeight(5);

                // row y columna que empiezan desde 0 para el controlador
                int row = i-1;
                int col = j-1;

                playerGrid.add(button, i, j);

                button.setOnAction(e -> {
                    Cell cell = playerBoard.getCell(row, col);
                    cell.markShot();
                    if (cell.hasShip()) {
                        button.setStyle("-fx-background-color: red;");
                    } else {
                        System.out.println("no ship");
                        button.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: red;");
                    }
                });

            }
        }

    }

    private void drawMachineGrid() {
        // Crear botones en cada celda que lancen un disparo cuando se hace clic
    }

    private void handlePlayerShot(int row, int col) {

    }

    public void updatePlayerGrid() {}
    public void updateMachineGrid() {}
}



