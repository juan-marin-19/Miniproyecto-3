package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;

public class PlacementeController {

    private Player player;

    public PlacementeController(){
    }

    public void startPlay(Player player){
        this.player = player;
        System.out.println(player.getSunkenShips());
        System.out.println(player.getNickname());
    }
}
