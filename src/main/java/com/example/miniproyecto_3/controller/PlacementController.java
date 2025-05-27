package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;
import com.example.miniproyecto_3.view.Figures;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class PlacementController {

    private Player player;
    private Board playerBoard;
    private int currentOrientation = 0;// 0 = horizontal, 1 = vertical

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


    public void initialize() {
        this.playerBoard = new Board();


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

       // metodo para dibujar el grid, manejar el evento click, y poner el barco
        drawBoard();


        //cargo las figuras y el codigo de su movimiento
        loadShipPalette(fragata1,1);
      //  loadShipPalette(fragata2,1);
        loadShipPalette(destructor1,2);

    }



    private void loadShipPalette(Group shipVersion,int version) {
        shipVersion.setUserData(new Ship(3, currentOrientation));

        //empieza el arrastre cuando se toca la figura
        shipVersion.setOnMousePressed(e -> {
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
            currentShip = new Ship(3, currentOrientation);

            if (version == 1) {
                floatingShip = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
            } else if (version == 2) {
                floatingShip = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
            }


            //añade el barco al anchorPane
            rootPane.getChildren().add(floatingShip);
            //"lo saca de la pantalla"
            floatingShip.setLayoutX(-1000);
            floatingShip.setLayoutY(-1000);

            floatingShip.setMouseTransparent(true); // no intercepta eventos


            //codigo para que la figura siga el mouse
            rootPane.setOnMouseMoved(ev -> {
                // la posicion se ajusta al mouse
                floatingShip.setLayoutX(ev.getSceneX() - 20);
                floatingShip.setLayoutY(ev.getSceneY() - 20);
            });


            //aqui se si se clikeo el player grid o no
            playerGrid.setOnMouseClicked(ev -> {
                System.out.println("se clikeo");
            });
        });

        //lo añado al vbox donde van los barcos
        shipPalette.getChildren().add(shipVersion);
    }




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

                //manejo la logica del modelo para ajustar donde colocar el barco
                clickArea.setOnMouseClicked(e -> {
                    System.out.println("se clickeo la posicion" + finalRow + "," + finalCol);
                    //drawShips(,);
                });

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
