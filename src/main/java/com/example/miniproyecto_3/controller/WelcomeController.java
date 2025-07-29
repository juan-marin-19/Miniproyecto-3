package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.miniproyecto_3.view.WelcomeStage;
import com.example.miniproyecto_3.view.GameStage;

/**
 * Class made for handling events in the main welcome window of the game.
 */
public class WelcomeController {

    private PlainTextFileHandler plainTextFileHandler;

    @FXML
    private TextField nickname;

    /**
     * Initializes the controller by creating an instance of the class responsible for handling plain text files.
     */
    public void initialize() {
        this.plainTextFileHandler = new PlainTextFileHandler();
    }

    /**
     * Handles the event of the play button from the welcome window.
     * It loads the window to place the ships, creates the player object,
     * and the plain text file.
     *
     * @param event action event from the button.
     */
    @FXML
    public void handleClickPlay(javafx.event.ActionEvent event) {

        if (!nickname.getText().isEmpty()) {
            Player player = new Player(nickname.getText().trim(), 0);
            String content = player.getNickname() + "," + player.getSunkenShips() + "," + "false";
            plainTextFileHandler.writeToFile("player_data.csv", content);

            WelcomeStage.deleteInstance();
            // Create a new instance of GameStage

            GameStage gameStage = new GameStage();
            gameStage.placementControllerStage();
            // Make sure the controller is not null before calling startPlay

            // TRY CATCH IN CASE GameController IS NULL
            try {
                PlacementController controller = gameStage.getPlacementController();
                if (controller != null) {
                    controller.setPlayer(player);
                } else {
                    throw new NullPointerException("PlacementController is null");
                }
            } catch (Exception e){
                System.out.println("Error while initializing the PlacementController:" + e.getMessage());
            }
        } else {
            System.out.println("The username is empty.");
        }
    }

    /**
     * Handles the event of the continue button.
     * It loads the game window and the player from the plain text file.
     *
     * @param event action event from the button.
     */
    @FXML
    public void handleClickContinue(javafx.event.ActionEvent event) {

        String[] data = plainTextFileHandler.readFromFile("player_data.csv");
        boolean ableToContinue = Boolean.parseBoolean(data[2]);
        if (ableToContinue) {
            String nickname = data[0];
            int sunkenBoats = Integer.parseInt(data[1]);
            Player player = new Player(nickname, sunkenBoats);
            // Player player = (Player) serializableFileHandler.deserialize("player_data.ser");

            WelcomeStage.deleteInstance();
            // Create a new instance of GameStage
            GameStage gameStage = new GameStage();
            gameStage.gameControllerStage();

            // Make sure the controller is not null before calling startGame

            try {
                GameController controller = gameStage.getGameController();
                if (controller != null) {
                    controller.setPlayer(player);
                    controller.startGame();
                } else {
                    throw new NullPointerException("GameController is null");
                }
            } catch (Exception e){
                System.out.println("Error while initializing the GameController:" + e.getMessage());
            }
        } else {
            System.out.println("no game available!!");
        }
    }

}
