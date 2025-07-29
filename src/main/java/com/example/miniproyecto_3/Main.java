package com.example.miniproyecto_3;

import com.example.miniproyecto_3.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class that launches the JavaFX application.
 * It initializes and displays the welcome window.
 */
public class Main extends Application {

    /**
     * The entry point of the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Called automatically when the application starts.
     * Loads the welcome window of the game.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        WelcomeStage.getInstance();
    }
}
