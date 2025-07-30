package com.example.miniproyecto_3.controller;

import javafx.event.ActionEvent;

/**
 * Interface that defines the responsibilities of a welcome controller.
 * A welcome controller manages the events in the main welcome window of the game,
 * such as starting a new game or continuing an existing one.
 */
public interface IWelcomeController {

    /**
     * Initializes the controller.
     * This method should create and configure the necessary
     * objects to manage plain text files or other resources.
     */
    void initialize();

    /**
     * Handles the event triggered when the play button is clicked
     * in the welcome window.
     * It should create a new player, save the data, and load the
     * ship placement window.
     *
     * @param event the action event from the play button
     */
    void handleClickPlay(ActionEvent event);

    /**
     * Handles the event triggered when the continue button is clicked
     * in the welcome window.
     * It should load the player data from file and continue directly
     * to the game window if a saved game is available.
     *
     * @param event the action event from the continue button
     */
    void handleClickContinue(ActionEvent event);
}
