package com.example.miniproyecto_3.view;

import com.example.miniproyecto_3.controller.PlacementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.example.miniproyecto_3.controller.GameController;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class handles the view of the main game window.
 * It can load both the placement view and the gameplay view.
 * It also handles loading controllers and applying styles.
 */
public class GameStage extends Stage {
    private GameController gameController;
    private Parent root;
    private PlacementController placementController;

    /**
     * Empty constructor for GameStage.
     */
    public GameStage(){}


    /**
     * Loads the game view window where the player plays the main part of the game.
     * Sets up the controller, scene, styles and icon.
     */
    public void gameControllerStage() {
        // Close any previous instance
        if (GameStageHolder.INSTANCE != null) {
            GameStageHolder.INSTANCE.close();
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/game-view.fxml"));
        try{
            root = loader.load();
            gameController = loader.getController();
        } catch (IOException e){
            System.out.println("Could not load the view");
            e.printStackTrace();
        }

        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/favicon-16x16.png"));
            getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error loading the icon: " + e.getMessage());
        }

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        scene.getStylesheets().add((getClass().getResource("/css/game-view-style.css")).toExternalForm());
        // Update the instance
        GameStageHolder.INSTANCE = this;
        show();
    }


    /**
     * Loads the ship placement window.
     * Sets up the controller, scene, styles, fonts and icon.
     */
    public void placementControllerStage(){
        // Close any previous instance
        if (GameStageHolder.INSTANCE != null) {
            GameStageHolder.INSTANCE.close();
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/placement-view.fxml"));
        try{
            root = loader.load();
            placementController = loader.getController();
        } catch (IOException e){
            System.out.println("Could not load the view");
            e.printStackTrace();
        }
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/favicon-16x16.png"));
            getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error loading the icon: " + e.getMessage());
        }

        // Manual loading of custom fonts
        InputStream fontStream = getClass().getResourceAsStream("/fonts/PirataOne-Regular.ttf");
        if (fontStream != null) {
            System.out.println("Font loaded successfully!");
        } else {
            System.out.println("ERROR: Font could not be loaded");
        }
        Font.loadFont(getClass().getResourceAsStream("/fonts/PirataOne-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/CinzelDecorative-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/CinzelDecorative-Regular.ttf"), 14);

        Scene scene = new Scene(root);
        scene.getStylesheets().add((getClass().getResource("/css/placement-style.css")).toExternalForm());
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        // Update the instance
        GameStageHolder.INSTANCE = this;
        show();
    }


    /**
     * Returns the controller for the game view.
     *
     * @return the GameController instance
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Returns the controller for the placement view
     *
     * @return the PlacementController instance
     */
    public PlacementController getPlacementController(){return placementController;}

    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }
}
