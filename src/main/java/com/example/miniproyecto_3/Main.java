package com.example.miniproyecto_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.miniproyecto_3.view.BattleshipStage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new BattleshipStage();
    }

    public static void main(String[] args) {
        launch();
    }
}