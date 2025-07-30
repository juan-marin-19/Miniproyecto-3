package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Player;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Interface that defines the responsibilities of a placement controller.
 * A placement controller manages drawing the grid, handling ship placement,
 * continuing to the game stage, and associating a player with the board.
 */
public interface IPlacementController {

    /**
     * Initializes the placement controller.
     * Should load ships, prepare the grid, and set up event listeners.
     */
    void initialize();

    /**
     * Loads a ship onto the grid and manages its placement.
     *
     * @param ship  the visual representation of the ship
     * @param size  the number of cells the ship occupies
     */
    void loadShip(Group ship, int size);

    /**
     * Draws the grid where ships can be placed.
     */
    void drawGrid();

    /**
     * Gets the reference cell from the GridPane to help place the ship.
     *
     * @param gridPane the container where ships are placed
     * @param row      the row index of the cell
     * @param col      the column index of the cell
     * @return the Node object inside the GridPane at the given coordinates,
     *         or null if not found
     */
    Node getCellPane(GridPane gridPane, int row, int col);

    /**
     * Continues the game after all ships have been placed.
     *
     * @param event the action event that triggers the continuation
     */
    void handleClickContinue(ActionEvent event);

    /**
     * Sets the player associated with the current board.
     *
     * @param player the player object storing player data
     */
    void setPlayer(Player player);
}
