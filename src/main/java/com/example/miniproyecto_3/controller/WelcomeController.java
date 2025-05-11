package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.miniproyecto_3.view.WelcomeStage;
import com.example.miniproyecto_3.view.GameStage;


import java.awt.event.ActionEvent;

public class WelcomeController {

    private PlainTextFileReader plainTextFileReader;

    private PlainTextFileReader plainTextFileReader2;

    @FXML
    private TextField nickname;

     public void initialize() {
    this.plainTextFileReader = new PlainTextFileReader();
    this.plainTextFileReader2 = new PlainTextFileReader();
     }


    @FXML
    public void handleClickPlay(javafx.event.ActionEvent event) {
        if (!nickname.getText().isEmpty()) {
            Player player = new Player(nickname.getText().trim(), 0);
            String content = player.getNickname() + "," + player.getSunkenShips();
            plainTextFileReader.writeToFile("player_data.csv",content);

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

    @FXML
    public void handleClickContinue(javafx.event.ActionEvent event) {
        String[] data = plainTextFileReader2.readFromFile("player_data.csv");

        String nickname = data[0];
        int sunkenBoats = Integer.parseInt(data[1]);
        Player player = new Player(nickname, sunkenBoats);
        // Player player = (Player) serializableFileHandler.deserialize("player_data.ser");

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

    }
}