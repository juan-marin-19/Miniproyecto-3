package com.example.miniproyecto_3.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.miniproyecto_3.controller.WelcomeController;

import java.io.IOException;

/**
 * This class creates and shows the welcome window of the game.
 * It loads the FXML layout, sets the style, and assigns the controller.
 */
public class WelcomeStage extends Stage {

    private WelcomeController welcomeController;
    private Parent root;


    /**
     * Constructor that loads the welcome view.
     * It sets the controller, scene, stylesheet, and icon.
     */
    public WelcomeStage() {
        super();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/welcome-view.fxml"));
        try{
            root = loader.load();
            welcomeController = loader.getController();
        } catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        //Cargar el icono
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/favicon-16x16.png"));
            getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }

        try {
            scene.getStylesheets().add((getClass().getResource("/css/welcome-view.css")).toExternalForm());
        }catch(Exception e){
            System.out.println("Error al cargar la hoja de estilo:" + e.getMessage());
        }
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        show();
    }


    /**
     * Static inner class that holds the single instance of WelcomeStage.
     * Used for singleton behavior.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }


    /**
     * Returns the current instance of WelcomeStage.
     * If there is no instance, it creates a new one.
     *
     * @return the WelcomeStage singleton instance
     */
    public static WelcomeStage getInstance() {
        WelcomeStageHolder.INSTANCE = (WelcomeStageHolder.INSTANCE != null ? WelcomeStageHolder.INSTANCE : new WelcomeStage());
        return WelcomeStageHolder.INSTANCE;
    }


    /**
     * Deletes the current instance of WelcomeStage
     * Closes the window and clears the instance.
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }

}

