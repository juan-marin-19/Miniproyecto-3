package com.example.miniproyecto_3.view;

import com.example.miniproyecto_3.controller.GameController;
import com.example.miniproyecto_3.controller.PlacementController;

/**
 * Interface of the main game window.
 */
public interface IGameStage {

    /**
     * Loads the game view window where the player plays the main part of the game.
     * Sets up the controller, scene, styles and icon.
     */
    void gameControllerStage();

    /**
     * Loads the ship placement window.
     * Sets up the controller, scene, styles, fonts and icon.
     */
    void placementControllerStage();

    /**
     * Returns the controller for the game view.
     *
     * @return the GameController instance
     */
    GameController getGameController();

    /**
     * Returns the controller for the placement view
     *
     * @return the PlacementController instance
     */
    PlacementController getPlacementController();
}