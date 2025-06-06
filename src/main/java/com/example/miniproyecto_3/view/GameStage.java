package com.example.miniproyecto_3.view;

import com.example.miniproyecto_3.controller.PlacementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.miniproyecto_3.controller.GameController;
import javafx.stage.StageStyle;

import java.io.IOException;

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
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sopa de letras");
        setResizable(false);
        initStyle(StageStyle.UNDECORATED);
        show();
    }

    public void GameStage2(){

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_3/placement-view.fxml"));
        try{
            root = loader.load();
            placementController = loader.getController();
        } catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sopa de letras");
        setResizable(false);
        initStyle(StageStyle.UNDECORATED);
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