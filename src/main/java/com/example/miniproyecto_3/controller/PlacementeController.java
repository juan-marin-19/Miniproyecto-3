package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Board;
import com.example.miniproyecto_3.model.Cell;
import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.model.Ship;
import com.example.miniproyecto_3.view.Figures;
import com.example.miniproyecto_3.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class PlacementeController {

    @FXML private GridPane GridPaneTable;
    @FXML private VBox shipContainer;
    @FXML private Pane FrigatePane;
    @FXML private Pane destroyerPane;
    @FXML private Pane submarinePane;
    @FXML private Pane carrierPane;
    @FXML private Button changeOrientationButton;
    @FXML private Label orientationLabel;
    @FXML private Button readyToPlayButton;

    private Player player;
    private Board playerBoard;
    private int selectedOrientation = 0; //0 = horizontal, 1 = vertical
    private int selectedShipSize = 0;
    private String selectedShipType = "";

    public PlacementeController(){
    }

    public void startPlay(Player player){
        this.player = player;
        System.out.println(player.getSunkenShips());
        System.out.println(player.getNickname());
        this.playerBoard = new Board();
        drawGrid();

    }
    @FXML
    public void initialize(){
        Group carrier = Figures.Carrier(Color.GRAY, Color.BLACK);
        Group submarine = Figures.Submarine(Color.GRAY, Color.BLACK);
        Group destroyer = Figures.Destroyer(Color.GRAY, Color.BLACK);
        Group frigate = Figures.Frigate(Color.GRAY, Color.BLACK);

        Figures.prepareFigures(carrier,carrierPane,50,200,0);
        Figures.prepareFigures(submarine,submarinePane,50,150,0);
        Figures.prepareFigures(destroyer,destroyerPane,50,100,0);
        Figures.prepareFigures(frigate,FrigatePane,50,50,0);
        carrierPane.getChildren().add(carrier);
        submarinePane.getChildren().add(submarine);
        destroyerPane.getChildren().add(destroyer);
        FrigatePane.getChildren().add(frigate);

        carrierPane.setOnMouseClicked(e -> selectShip("Carrier", 4));
        submarinePane.setOnMouseClicked(e -> selectShip("submarine", 3));
        destroyerPane.setOnMouseClicked(e -> selectShip("destroyer",2));
        FrigatePane.setOnMouseClicked(e -> selectShip("frigate",1));


    }

    public void selectShip(String type, int size){
        selectedShipSize = size;
        selectedShipType = type;
        System.out.printf("Barco seleccionado" + type + "Tamaño:" + size +"\n");
    }

    public void drawGrid() {
        GridPaneTable.getChildren().clear();
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                Rectangle rect = new Rectangle(40,40);
                rect.setFill(Color.LIGHTBLUE);
                rect.setStroke(Color.BLACK);
                StackPane cell = new StackPane(rect);
                rect.widthProperty().bind(cell.widthProperty());
                rect.heightProperty().bind(cell.heightProperty());
                GridPane.setHgrow(cell, Priority.ALWAYS);
                GridPane.setVgrow(cell, Priority.ALWAYS);
                final int r = row;
                final int c = col;
                cell.setOnMouseClicked(e -> {if(e.getButton() == MouseButton.PRIMARY) {
                    placeShipAt(r,c);
                    System.out.println("Se selecciono la celda");
                }
                });
                GridPaneTable.add(cell,col,row);
            }
        }
        System.out.println("Se dibujo exitosamente el tablero");
    }

    public void placeShipAt(int row, int col){
        if(selectedShipType == null){
            System.out.println("Seleccione un barco");
            return;
        }
        Cell a = playerBoard.getCell(row,col);
        if(a.hasShip()){
            System.out.println("La celda ya tiene un barco");
            return;
        }
        Ship ship = new Ship(selectedShipSize, selectedOrientation);
        boolean placed = playerBoard.placeShip(ship,row,col);
        if(placed){
            Group figure = switch (selectedShipType){
                case "Carrier" -> Figures.Carrier(Color.DARKGRAY, Color.BLACK);
                case "submarine" -> Figures.Submarine(Color.DARKGRAY, Color.BLACK);
                case "destroyer" -> Figures.Destroyer(Color.DARKGRAY, Color.BLACK);
                case "frigate" -> Figures.Frigate(Color.DARKGRAY, Color.BLACK);
                default -> null;
            };
            System.out.println("Se creo la figura");

            if(figure != null){
                double baseHeight = switch(selectedShipType){
                    case "Carrier" -> 200;
                    case "submarine" -> 150;
                    case "destroyer" -> 100;
                    case "frigate" -> 50;
                    default -> 100;
                };

                double baseWidth = 50;

                Pane shipPane = new Pane();
                shipPane.setPickOnBounds(false);
                GridPaneTable.add(shipPane, col, row);
                if(selectedOrientation == 0){
                    GridPane.setColumnSpan(shipPane, selectedShipSize);
                }else{
                    GridPane.setRowSpan(shipPane, selectedShipSize);
                }

                GridPaneTable.applyCss();
                GridPaneTable.layout();

                Figures.prepareFigures(figure, shipPane, baseWidth, baseHeight, selectedOrientation);
                shipPane.getChildren().add(figure);

                selectedShipType = null;

                System.out.println("Se posicionó la fugira con exito");

            } else{
                System.out.println("No se pudo colocar el barco en esa posición");
            }

        }
    }

    public void handleRotate(){
        selectedOrientation = (selectedOrientation == 0) ? 1:0;
        //aqui se cambia tmabien el texto en el label para especificar la orientación
        System.out.println("Se cambio la orientacion");
    }
    public void handleReadyToPlay(){
        GameStage.deleteInstance();
        GameStage gameStage = new GameStage();
        gameStage.GameStage1();
        GameController controller = gameStage.getGameController();
        if(controller != null){
            //aqui se le tienen que pasar le tablero a el controlador
        }else{
            System.out.println("el controlador es null");
        }

    }
}
