package com.example.miniproyecto_3;

import com.example.miniproyecto_3.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;
import com.example.miniproyecto_3.view.GameStage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WelcomeStage.getInstance();
    }


}