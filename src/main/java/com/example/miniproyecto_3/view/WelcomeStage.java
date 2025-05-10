package com.example.miniproyecto_3.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeStage extends Stage {


        public WelcomeStage() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto_3/welcome-view.fxml"));
            Scene scene = new Scene(loader.load(), 965,580 );
            setTitle("Battleship");
            setResizable(false);
            setScene(scene);
            show();

        }
    }

