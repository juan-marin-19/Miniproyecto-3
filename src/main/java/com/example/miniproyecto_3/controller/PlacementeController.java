package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Board;
import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.view.Figures;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class PlacementeController {

    @FXML private GridPane GridPaneTable;
    @FXML private VBox shipContainer;
    @FXML private Pane FrigatePane;
    @FXML private Pane DestroyerPane;
    @FXML private Pane SubmarinePane;
    @FXML private Pane CarrierPane;
    @FXML private Button ChageorientationButton;
    @FXML private Label OrientationLabel;
    @FXML private Button readyToPlayButton;

    private Player player;
    private Board playerBoard;
    private int selectedOrientation = 0; //0 = horizontal, 1 = vertical

    public PlacementeController(){
    }

    public void startPlay(Player player){
        this.player = player;
        System.out.println(player.getSunkenShips());
        System.out.println(player.getNickname());
    }
    @FXML
    public void initialize(){
        Group carrier = Figures.Carrier(Color.GRAY, Color.BLACK);
        Group submarine = Figures.Submarine(Color.GRAY, Color.BLACK);
        Group destroyer = Figures.Destroyer(Color.GRAY, Color.BLACK);
        Group frigate = Figures.Frigate(Color.GRAY, Color.BLACK);

        Figures.prepareFigures(carrier,CarrierPane,50,200,0);
        Figures.prepareFigures(submarine,SubmarinePane,50,150,0);
        Figures.prepareFigures(destroyer,DestroyerPane,50,100,0);
        Figures.prepareFigures(frigate,FrigatePane,50,50,0);
        CarrierPane.getChildren().add(carrier);
        SubmarinePane.getChildren().add(submarine);
        DestroyerPane.getChildren().add(destroyer);
        FrigatePane.getChildren().add(frigate);
    }

    public void handleReadyToPlay(){

    }
}
