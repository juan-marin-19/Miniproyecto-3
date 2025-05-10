package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import javafx.fxml.FXML;


import java.awt.event.ActionEvent;

public class WelcomeController {

    private PlainTextFileReader plainTextFileReader;


     public void initialize() {
    this.plainTextFileReader = new PlainTextFileReader();
     }


    @FXML
    public void handleClickPlay(javafx.event.ActionEvent actionEvent) {
         System.out.println("Play");
    }
}