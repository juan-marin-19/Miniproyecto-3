package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.Board;

import com.example.miniproyecto_3.model.Machine;
import com.example.miniproyecto_3.model.planeSerializableFiles.SeriazableFileHandler;
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

    private Player player;
    private Machine machine;
    private Board playerBoard;
    private Board machineBoard;
    private SeriazableFileHandler seriazableFileHandler;





    public GameController() {
    }

    public void initialize() {

        //se desserializa el board del jugador
        try{
            this.playerBoard = (Board) seriazableFileHandler.deserialize("board_data.ser");
            System.out.println("se cargo exitosamente el tablero");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error al cargar el tablero");
            this.playerBoard = new Board(10, 10); // Solo si no fue seteado antes
        }

        this.machineBoard = new Board(10,10);
        drawPlayerGrid();
        playerBoard.printCellGrid();

        this.seriazableFileHandler = new SeriazableFileHandler();

    }


    private void drawPlayerGrid() {

        // Mostrar botones o celdas con barcos

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                boolean shipThere = playerBoard.getCell(j-1,i-1).getShip() != null;

                Button button = new Button();

                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                button.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightblue;");

                if(shipThere)  button.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: red;");

                // button.setPrefWidth(10);   // Ancho preferido
              //  button.setPrefHeight(5);

                // row y columna que empiezan desde 0 para el controlador
                int row = i-1;
                int col = j-1;

                playerGrid.add(button, i, j);



               //boton para manejar los disparo

            }
        }

        System.out.println(" ");

    }

    private void drawMachineGrid() {
        // Crear botones en cada celda que lancen un disparo cuando se hace clic
    }

    private void handlePlayerShot(int row, int col) {

    }

    /**
     * METODO donde se inicia la ventana de juego, se dibuja la pantalla luego de que board se halla puesto una vez se termino
     * de colocar en el placement controller.
     * */

    public void startGame() {
        playerBoard = (Board) seriazableFileHandler.deserialize("player_board.ser");
        drawPlayerGrid();
        playerBoard.printCellGrid();
    }

    /**
     * Coloco el player ya sea desde el controlador placement o si ya había una partida, coloco el player con los datos
     * guardados (nombre y barcos hundidos)
     * */
    public void setPlayer(Player player) {
        this.player = player;
        System.out.println(player.getSunkenShips());
        System.out.println(player.getNickname());
    }

    public void updatePlayerGrid() {}
    public void updateMachineGrid() {}

}



