package com.example.miniproyecto_3.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.miniproyecto_3.controller.WelcomeController;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;


public class WelcomeStage extends Stage {
    private WelcomeController welcomeController;
    private Parent root;

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
            scene.getStylesheets().add((getClass().getResource("/css/welcomeViewStyle.css")).toExternalForm());
        }catch(Exception e){
            System.out.println("Error al cargar la hoja de estilo:" + e.getMessage());
        }
        setScene(scene);
        setTitle("Battleship");
        setResizable(false);
        //initStyle(StageStyle.UNDECORATED);  ¿Por qué esto?
        show();
    }

    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    public static WelcomeStage getInstance() {
        WelcomeStageHolder.INSTANCE = (WelcomeStageHolder.INSTANCE != null ? WelcomeStageHolder.INSTANCE : new WelcomeStage());
        return WelcomeStageHolder.INSTANCE;
    }

    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }

}

