package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Board;
import com.example.miniproyecto_3.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



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
}
