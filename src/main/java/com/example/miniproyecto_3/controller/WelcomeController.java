package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.miniproyecto_3.view.WelcomeStage;
import com.example.miniproyecto_3.view.GameStage;

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
            Player player = new Player(nickname.getText().trim(), 0);
            String content = player.getNickname() + "," + player.getSunkenShips() + "," + "false";
            plainTextFileReader.writeToFile("player_data.csv", content);

            WelcomeStage.deleteInstance();
            // Crea una nueva instancia de GameStage

            GameStage gameStage = new GameStage();
            gameStage.GameStage2();
            // Asegúrate de que el controlador no sea null antes de llamar startPlay

            //AQUI VA UN TRY CATCH PARA EL GAMECONTROLLER NULL

            PlacementController controller = gameStage.getPlacementController();
            // Asegúrate de que el controlador no sea null antes de llamar startPlay

            //AQUI VA UN TRY CATCH PARA EL GAMECONTROLLER NULL

            if (controller != null) {
                controller.setPlayer(player);
            } else {
                System.err.println("Error: Placecontroller is null");
            }
        } else {
            System.out.println("El nombre de usuario está vacío.");
        }
    }


    @FXML
    public void handleClickContinue(javafx.event.ActionEvent event) {

        String[] data = plainTextFileReader.readFromFile("player_data.csv");
        boolean ableToContinue = Boolean.parseBoolean(data[2]);
        if (ableToContinue) {
            String nickname = data[0];
            int sunkenBoats = Integer.parseInt(data[1]);
            Player player = new Player(nickname, sunkenBoats);
            // Player player = (Player) serializableFileHandler.deserialize("player_data.ser");

            WelcomeStage.deleteInstance();
            // Crea una nueva instancia de GameStage
            GameStage gameStage = new GameStage();
            gameStage.GameStage1();

            // Asegúrate de que el controlador no sea null antes de llamar startPlay

            //AQUI VA UN TRY CATCH PARA EL GAMECONTROLLER NULL

            GameController controller = gameStage.getGameController();
            if (controller != null) {
                controller.setPlayer(player);
                controller.startGame();
            } else {
                System.err.println("Error: GameController is null");
            }
        } else {
            System.out.println("no hay partida!!");
        }




    }

}
