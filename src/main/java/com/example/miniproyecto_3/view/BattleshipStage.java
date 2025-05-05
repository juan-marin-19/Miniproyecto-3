package com.example.miniproyecto_3.view;

import com.example.miniproyecto_3.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleshipStage extends Stage {


    public BattleshipStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto_3/battleship.fxml"));
        Scene scene = new Scene(loader.load(), 965,580 );
        setTitle("Battleship");
        setResizable(false);
        setScene(scene);
        show();

    }
}
