package com.example.miniproyecto_3.controller;

import com.example.miniproyecto_3.model.Board;
import com.example.miniproyecto_3.model.Player;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Interface representing the controller  of game view
 */
public interface IGameController {
    /**
     * Initializes the boards and the objects that manage the files.
     */
    void initialize();

    /**
     * Starts the game window, draws the screen, deserializes the boards,
     * and calls the method that handles the players shot.
     */
    void startGame();

    /**
     * Visually places the ships on the GridPane using their first cell as reference.
     *
     * @param board     board of the player or the machine
     * @param gridPane  container of the board (position or main)
     * @param cheatMode boolean to know if the opponents ships can be shown
     */
    void drawShips(Board board, GridPane gridPane, boolean cheatMode);


    /**
     * Method with an event handler that checks if a cell was clicked and acts accordingly,
     * handling the players shot, if it hits a ship, destroys it or if it hits nothing, machine will shoot.
     */
    void handlePlayerShot();

    /**
     * Code where the machine makes a shot using its board. If it hits or sinks a ship, it will shoot again.
     */
    void handleMachineShot();

    /**
     * Updates the visual elements of the gridPane using the given board.
     *
     * @param board    the board of the player or the machine
     * @param gridPane the container of the grid (main or position board)
     */
    void updateGridVisuals(Board board, GridPane gridPane);

    /**
     * If a ship is sunken, updates the number of sunken ships by player or machine.
     * Also updates the labels and the content of the plain text files
     *
     * @param board         the board of the player or the machine
     * @param isPlayerBoard boolean to know which board it is
     */
    void updateSunkenShipsCount(Board board, boolean isPlayerBoard);

    /**
     * Handles the end of the game, disables functions, shows a final message, and updates a plain text file.
     *
     * @param message message to show in an alert window
     */
    void endGame(String message);

    /**
     * Resets the auxiliary boolean variables used to know if a ship was already shown on the board.
     * Used for when the window is closed and opened again.
     *
     * @param board the board of the player or the machine
     */
    void resetAux(Board board);


    /**
     * Serializes the boards after a change.
     */
    void saveGame();


    /**
     * Setter for the player object, which stores the player's information.
     *
     * @param player object that stores the player info
     */
    void setPlayer(Player player);


    /**
     * Same method as in PlacementController
     * Gets the cell from the gridPane at a specific row and column.
     */
    Node getCellPane(GridPane gridPane, int row, int col);
}
