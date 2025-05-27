package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;
import com.example.miniproyecto_3.view.Figures;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class PlacementController {

    private Player player;
    private Board playerBoard;
    public int currentShipId;
    public int currentOrientation = 0;// 0 = horizontal, 1 = vertical
    private List<Group> shipGraphics = new ArrayList<>();
    private Ship currentShip = null;
    private Group floatingShip;

    @FXML
    private AnchorPane rootPane; // tu AnchorPane raíz que contiene todo

    @FXML
    private GridPane playerGrid;

    @FXML
    private VBox shipPalette;


    public PlacementController() {
    }


    /**
     * CARGO las figuras, y las funciones para el movimiento, dibujo de todo , y los clicks
     */
    public void initialize() {
        this.playerBoard = new Board();


        // creo las figuras y las añado a una lista de figuras para poder quitarlas luego
        // version 1 = fragata, 2= destructor
        Group fragata1 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata2 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata3 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata4 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group destructor1 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group destructor2 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group destructor3 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group submarine1 = Figures.Submarine(Color.LIGHTBLUE, Color.BLACK);
        Group submarine2 = Figures.Submarine(Color.LIGHTBLUE, Color.BLACK);
        Group portaaviones = Figures.Portaaviones(Color.LIGHTBLUE, Color.BLACK);


        shipGraphics.add(fragata1);
        shipGraphics.add(fragata2);
        shipGraphics.add(fragata3);
        shipGraphics.add(fragata4);
        shipGraphics.add(destructor1);
        shipGraphics.add(destructor2);
        shipGraphics.add(destructor3);
        shipGraphics.add(submarine1);
        shipGraphics.add(submarine2);
        shipGraphics.add(portaaviones);

        // metodo para dibujar el grid, manejar el evento click, y poner el barco
        drawBoard();


        //cargo las figuras y el codigo de su movimiento, (version = size)

        loadShipPalette(fragata1,1,0);
        loadShipPalette(fragata2,1,1);
        loadShipPalette(fragata3,1,2);
        loadShipPalette(fragata4,1,3);
        loadShipPalette(destructor1,2,4);
        loadShipPalette(destructor2,2,5);


    }


    /**
    * MOVIMIENTO Y COLOCACIÓN DEL BARCO
    */
    private void loadShipPalette(Group shipVersion,int version, int currentShipId) {

            shipVersion.setUserData(new Ship(version, currentOrientation));

            //empieza el arrastre cuando se toca la figura
            shipVersion.setOnMousePressed(e -> {

                this.currentShipId = currentShipId;

                if (currentShip != null && floatingShip != null) {
                    // Ya hay un barco activo: cancelar selección
                    rootPane.getChildren().remove(floatingShip);
                    rootPane.setOnMouseMoved(null);
                    floatingShip = null;
                    currentShip = null;
                    System.out.println("Selección cancelada");
                    return;
                }

                // creo un ship aux para ser usado luego
                currentShip = new Ship(version, currentOrientation);

                if (version == 1) {
                    floatingShip = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                } else if (version == 2) {
                    floatingShip = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                }

                //añade el barco al anchorPane
                rootPane.getChildren().add(floatingShip);


                //lo coloco para que siga el mouse
                floatingShip.setLayoutX(e.getSceneX() - 20);
                floatingShip.setLayoutY(e.getSceneY() - 20);


                floatingShip.setMouseTransparent(true); // no intercepta eventos


                //codigo para que la figura siga el mouse
                rootPane.setOnMouseMoved(ev -> {
                    // la posicion se ajusta al mouse
                    floatingShip.setLayoutX(ev.getSceneX() - 20);
                    floatingShip.setLayoutY(ev.getSceneY() - 20);
                });


            });

            //lo añado al vbox donde van los barcos
            shipPalette.getChildren().add(shipVersion);
        }


/**
 *Dibujo el tablero, agrego regiones, manejo el ckick, dibujo el barco en el tablero
 */
    private void drawBoard() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {


                // agrega un stackpane para poder superponer los barcos
                StackPane cell = new StackPane();
                cell.setPrefSize(40, 40);
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");


                // Agrega una zona invisible para clics
                Region clickArea = new Region();
                clickArea.setPrefSize(40, 40);
                clickArea.setStyle("-fx-background-color: transparent;");

                int finalRow = j-1;
                int finalCol = i-1;


                //manejo la logica del modelo para ajustar donde colocar el barco y el controlador para hacerlo visualmente
                clickArea.setOnMouseClicked(e -> {

                    if (currentShip != null && floatingShip != null) {
                        // 1. Eliminar barco flotante visual
                        rootPane.getChildren().remove(floatingShip);
                        rootPane.setOnMouseMoved(null);



                        //IMPORTANTE!!!!!!!!!!!!
                        // 2. Colocar visualmente el barco en la celda usando drawship y metodos del modelo!!!!!




                        // 3. Limpia estado actual y elimina del vbox el barco con la referencia del barco en la lista
                        currentShip = null;
                        floatingShip = null;
                        shipPalette.getChildren().remove(shipGraphics.get(currentShipId));



                        System.out.println("Barco colocado en " + finalRow + "," + finalCol);

                    } else {
                        System.out.println("No hay barco seleccionado, seleccione un barco ");
                    }

                });

                //añado la region al stackpane para que se pueda superponer la figura
                cell.getChildren().add(clickArea);
                playerGrid.add(cell, i, j);
            }

        }

    }




    private void drawShips(Ship ship,int row,int col){


    }


    private StackPane getCellPane ( int row, int col){
            for (Node n : playerGrid.getChildren()) {
                if (GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == col) {
                    return (StackPane) n;
                }
            }
            return null;
        }




        public void startPlay (Player player){
            this.player = player;
            System.out.println(player.getSunkenShips());
            System.out.println(player.getNickname());
        }


}
