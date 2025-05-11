package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;

public class GameController {

    private Player player;

    public GameController() {
        // No debe tener parámetros
    }
    public void startPlay(Player player){
        this.player = player;
        System.out.println(player.getSunkenShips());
        System.out.println(player.getNickname());
    }
}
