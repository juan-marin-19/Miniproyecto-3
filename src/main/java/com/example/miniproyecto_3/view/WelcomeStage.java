package com.example.miniproyecto_3.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        scene.getStylesheets().add((getClass().getResource("/css/welcomeViewStyle.css")).toExternalForm());
        setScene(scene);
        setTitle("Sopa de letras");
        setResizable(false);
        initStyle(StageStyle.UNDECORATED);
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

