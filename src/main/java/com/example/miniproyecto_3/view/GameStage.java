package com.example.miniproyecto_3.view;

import com.example.miniproyecto_3.controller.PlacementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.example.miniproyecto_3.controller.GameController;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class GameStage extends Stage {
    private GameController gameController;
    private Parent root;
    private PlacementController placementController;

    public GameStage(){}

    public void GameStage1() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/game-view.fxml"));
        try{
            root = loader.load();
            gameController = loader.getController();
        } catch (IOException e){
            System.out.println("No se pudo cargar la vista");
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        show();
    }

    public void GameStage2(){

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/placement-view.fxml"));
        try{
            root = loader.load();
            placementController = loader.getController();
        } catch (IOException e){
            System.out.println("No se pudo cargar la vista");
            e.printStackTrace();
        }
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/favicon-16x16.png"));
            getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }

        // Carga manual de las fuentes personalizadas
        InputStream fontStream = getClass().getResourceAsStream("/fonts/PirataOne-Regular.ttf");
        if (fontStream != null) {
            System.out.println("¡Fuente encontrada correctamente!");
        } else {
            System.out.println("ERROR: No se pudo cargar la fuente");
        }
        Font.loadFont(getClass().getResourceAsStream("/fonts/PirataOne-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/CinzelDecorative-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/CinzelDecorative-Regular.ttf"), 14);

        Scene scene = new Scene(root);
        scene.getStylesheets().add((getClass().getResource("/css/placement-style.css")).toExternalForm());
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        show();
    }


    public GameController getGameController() {
        return gameController;
    }

    public PlacementController getPlacementController(){return placementController;}

    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    public static GameStage getInstance() {
        GameStageHolder.INSTANCE = (GameStageHolder.INSTANCE != null ? GameStageHolder.INSTANCE : new GameStage());
        return GameStageHolder.INSTANCE;
    }

    public static void deleteInstance() {
        if (GameStageHolder.INSTANCE != null) {
            GameStageHolder.INSTANCE.close();
            GameStageHolder.INSTANCE = null;
        } else {
            System.out.println("No hay instancia activa de GameStage para cerrar.");
        }
    }

}