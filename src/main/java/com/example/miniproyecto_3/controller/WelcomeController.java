package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.miniproyecto_3.view.WelcomeStage;
import com.example.miniproyecto_3.view.GameStage;


import java.awt.event.ActionEvent;

public class WelcomeController {

    private PlainTextFileReader plainTextFileReader;

    @FXML
    private TextField nickname;

     public void initialize() {
    this.plainTextFileReader = new PlainTextFileReader();
     }


    @FXML
    public void handleClickPlay(javafx.event.ActionEvent event) {
        if (!nickname.getText().isEmpty()) {
            Player player = new Player(nickname.getText(), 0);
            WelcomeStage.deleteInstance();

            // Crea una nueva instancia de GameStage
            GameStage gameStage = new GameStage();

            // Asegúrate de que el controlador no sea null antes de llamar startPlay

            //AQUI VA UN TRY CATCH PARA EL GAMECONTROLLER NULL

            GameController controller = gameStage.getGameController();
            if (controller != null) {
                controller.startPlay(player);
            } else {
                System.err.println("Error: GameController is null");
            }
        } else {
            System.out.println("El nombre de usuario está vacío.");
        }
    }

    //creando boton de cerrar porque el otro no se percibe bien
    public void handleClose(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
    }
}