package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;
import com.example.miniproyecto_3.model.planeSerializableFiles.SeriazableFileHandler;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileHandler;
import com.example.miniproyecto_3.view.Figures;
import com.example.miniproyecto_3.view.GameStage;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import com.example.miniproyecto_3.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the ship placement and switches to the game window.
 */
public class PlacementController {

    private Player player;
    private boolean dragging = false; // True if a ship is being dragged false otherwise; used to know if its posible to rotate a ship
    private boolean rotated = false; // rotated; vertical = false ; horizontal = true
    private boolean resetFinalCoords = false;
    private Board playerBoard;
    private Group currentShipVisual = null; // Reference to manage a ships visual placement when its rotated
    private List<int[]> shipFinalCoords = new ArrayList<>(); // List of arrays int use to store the ships coordinates
    private int numShipsPlaced = 0;
    private final SeriazableFileHandler seriazableFileHandler;
    private final PlainTextFileHandler plainTextFileHandler;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private GridPane playerGrid;

    @FXML
    private FlowPane shipPalette;

    @FXML
    private BorderPane borderPane;  // Main layout


    /**
     * Initializes the objects that handle plain text and serialized files.
     */
    public PlacementController() {
        this.plainTextFileHandler = new PlainTextFileHandler();
        this.seriazableFileHandler = new SeriazableFileHandler();
    }


    /**
     * Loads the ship shapes, draws the grid, creates the player board,
     * and handles the event to change ship orientation.
     *
     * @see #drawGrid() 
     * @see #loadShip(Group, int)
     */
    public void initialize() {
        this.playerBoard = new Board(10, 10);

        //Set a fixed height to avoid errors.
        rootPane.setMinHeight(500);
        rootPane.setMaxHeight(600);
        rootPane.setPrefHeight(600);


        //Code that handles the orientation change
        borderPane.setOnKeyPressed(event -> {
            if (dragging && event.getCode() == KeyCode.G && currentShipVisual != null) {
                rotated = !rotated;
                currentShipVisual.setRotate(rotated ? 270 : 0);

                // Color all the grid back to normal.
                for (Node node : playerGrid.getChildren()) {
                    if (node instanceof StackPane) {
                        node.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");
                    }
                }


                //Code to make sure the ship is placed correctly when rotated even if it's not dragged after it's been so
                Bounds shipBounds = currentShipVisual.localToScene(currentShipVisual.getBoundsInLocal());
                boolean isVertical = !rotated;

                double refX, refY;
                if (isVertical) {
                    if (shipFinalCoords.size() == 1) {
                        refX = shipBounds.getMinX() + shipBounds.getWidth() / 2;
                        refY = shipBounds.getMinY() + shipBounds.getHeight() / 2.1;
                    } else {
                        refX = shipBounds.getMinX() + shipBounds.getWidth() / 2;
                        refY = shipBounds.getMinY() + shipBounds.getHeight() / 2;
                    }
                } else {
                    if (shipFinalCoords.size() == 1) {
                        refX = shipBounds.getMinX() + shipBounds.getWidth() / 2.1;
                        refY = shipBounds.getMinY() + shipBounds.getHeight() / 2;
                    } else {
                        refX = shipBounds.getMinX() + shipBounds.getWidth() / 2;
                        refY = shipBounds.getMinY() + shipBounds.getHeight() / 2;
                    }
                }

                for (Node node : playerGrid.getChildren()) {
                    if (node instanceof StackPane) {
                        Bounds cellBounds = node.localToScene(node.getBoundsInLocal());
                        if (cellBounds.contains(refX, refY)) {
                            Integer col = GridPane.getColumnIndex(node);
                            Integer row = GridPane.getRowIndex(node);
                            if (col == null) col = 0;
                            if (row == null) row = 0;
                            List<int[]> newCoords = playerBoard.getCoordinatesForShip(row, col, shipFinalCoords.size(), isVertical);
                            if (!newCoords.isEmpty()) {
                                shipFinalCoords = newCoords;
                                resetFinalCoords = false;
                            } else {
                                resetFinalCoords = true;
                                shipFinalCoords.clear();
                            }
                            break;
                        }
                    }
                }
            }
        });


        Group frigate1 = Figures.frigate(Color.GRAY, Color.BLACK);
        Group frigate2 = Figures.frigate(Color.GRAY, Color.BLACK);
        Group frigate3 = Figures.frigate(Color.GRAY, Color.BLACK);
        Group frigate4 = Figures.frigate(Color.GRAY, Color.BLACK);
        Group destroyer1 = Figures.destroyer(Color.GRAY, Color.BLACK);
        Group destroyer2 = Figures.destroyer(Color.GRAY, Color.BLACK);
        Group destroyer3 = Figures.destroyer(Color.GRAY, Color.BLACK);
        Group submarine1 = Figures.Submarine(Color.GRAY, Color.BLACK);
        Group submarine2 = Figures.Submarine(Color.GRAY, Color.BLACK);
        Group aircraftCarrier = Figures.aircraftCarrier(Color.GRAY, Color.BLACK);

        drawGrid();

        loadShip(frigate1, 1);
        loadShip(frigate2, 1);
        loadShip(frigate3, 1);
        loadShip(frigate4, 1);
        loadShip(destroyer1, 2);
        loadShip(destroyer2, 2);
        loadShip(destroyer3, 2);
        loadShip(submarine1, 3);
        loadShip(submarine2, 3 );
        loadShip(aircraftCarrier, 4);


    }


    /**
     * Adds the ship figures to the flowPane, deals with their movement on the screen,
     * and manages their correct position in both the grid and model board.
     *
     * @param shipVersion the ship figure
     * @param version the ship size and number of cells it occupies
     *
     * @see #getCellPane(GridPane, int, int)
     */
    private void loadShip(Group shipVersion, int version) {

        shipVersion.setScaleX(0.78);
        shipVersion.setScaleY(0.78);
        shipPalette.getChildren().add(shipVersion);

        // Save the highlighted cells to color them later
        final List<StackPane> previouslyHighlighted = new ArrayList<>();

        // Press a ship
        shipVersion.setOnMousePressed(event -> {

            // adjust visual orientation
            dragging = true;
            currentShipVisual = shipVersion;

            // Move the ship from the FlowPane root to the AnchorPane so it appears on top
            Bounds shipBounds = shipVersion.localToScene(shipVersion.getBoundsInLocal());

            shipPalette.getChildren().remove(shipVersion);
            rootPane.getChildren().add(shipVersion);

            // Place the ship at the position where it was clicked (relative to the scene)
            shipVersion.setLayoutX(shipBounds.getMinX());
            shipVersion.setLayoutY(shipBounds.getMinY());
        });

        // Dragging a ship
        shipVersion.setOnMouseDragged(event -> {

            shipVersion.toFront();

            rootPane.setCursor(Cursor.NONE);

            // Get the ship center in scene coordinates
            Bounds shipBounds = shipVersion.localToScene(shipVersion.getBoundsInLocal());

            // Detect current ship orientation (based on rotation)
            boolean isVertical = !(Math.abs(shipVersion.getRotate() % 180) == 90);

            // Update ship position visually, moved approximately from the center
            if (version == 3 ) {
                if (isVertical) {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 1.8);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.5);
                } else {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 2.3);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.8);
                }
            } else if (version == 4) {
                if (isVertical) {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 1.8);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 4.5);
                } else {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 2.3);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.4);
                }
            } else if (version == 1) {
                if (isVertical) {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 1.4);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight());
                } else {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth());
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.4);
                }
            } else {
                if (isVertical) {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 1.34);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.15);
                } else {
                    shipVersion.setLayoutX(event.getSceneX() - shipVersion.getBoundsInLocal().getWidth() / 1.7);
                    shipVersion.setLayoutY(event.getSceneY() - shipVersion.getBoundsInLocal().getHeight() / 1.5);
                }
            }

            // Color the cells back to normal
            for (StackPane cell : previouslyHighlighted) {
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");
            }
            previouslyHighlighted.clear();

            double referencePointX;
            double referencePointY;

            // Calculate the reference point to determine which cells the ship occupies
            if (isVertical) {
                if (version == 1) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 1.96;
                } else if (version == 3) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.2;
                } else if (version == 4) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.37;
                } else {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.1;
                }
            } else {
                if (version == 1) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 1.96;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else if (version == 3) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.35;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else if (version == 4) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.55;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.1;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                }
            }

            // Code to highlight the placement and store coordinates if valid
            for (Node node : playerGrid.getChildren()) {
                if (node instanceof StackPane) {
                    Bounds cellBounds = node.localToScene(node.getBoundsInLocal());

                    // Get row and column of the cell that contains the reference point
                    if (cellBounds.contains(referencePointX, referencePointY)) {
                        Integer col = GridPane.getColumnIndex(node);
                        Integer row = GridPane.getRowIndex(node);

                        // Avoid nulls (may occur in cells without explicit set)
                        if (col == null) col = (Integer) 0;
                        if (row == null) row =  (Integer) 0;

                        // Get coordinates from model and store them
                        List<int[]> shipCoords = playerBoard.getCoordinatesForShip(row, col, version, isVertical);
                        shipFinalCoords = shipCoords;
                        resetFinalCoords = shipCoords.isEmpty();

                        // Highlight if there's space
                        if (!shipCoords.isEmpty()) {
                            for (int[] coord : shipCoords) {
                                int r = coord[0];
                                int c = coord[1];

                                for (Node n : playerGrid.getChildren()) {
                                    if (n instanceof StackPane) {
                                        Integer nc = GridPane.getColumnIndex(n);
                                        Integer nr = GridPane.getRowIndex(n);
                                        if (nc != null && nr != null && nc == c && nr == r) {
                                            n.setStyle("-fx-border-color: black; -fx-background-color: red;");
                                            previouslyHighlighted.add((StackPane) n);
                                        }
                                    }
                                }
                            }
                        }
                        break; // Already found the center cell
                    } else {
                        resetFinalCoords = true;
                    }
                }
            }

            event.consume();

        });

        // Drop a ship
        shipVersion.setOnMouseReleased(event -> {

            if(resetFinalCoords){
                shipFinalCoords = new ArrayList<>();
            }

            if(!shipFinalCoords.isEmpty()) {

                // Add the ship to the model board
                playerBoard.placeShip(shipFinalCoords,rotated);

                // Add ship to the grid visually
                System.out.println(shipFinalCoords.get(0)[0] + " " + shipFinalCoords.get(0)[1]);

                Node referenceCell = getCellPane(playerGrid,shipFinalCoords.get(0)[0], shipFinalCoords.get(0)[1]);
                Bounds cellBounds = referenceCell.localToScene(referenceCell.getBoundsInLocal());
                double pivotX = cellBounds.getWidth() / 2;
                double pivotY = cellBounds.getHeight() / 2;

                if(!rotated){
                    if (version == 1){
                        shipVersion.setLayoutX(cellBounds.getMinX()-4.5);
                        shipVersion.setLayoutY(cellBounds.getMinY()-93);
                    } else if (version == 2) {
                        shipVersion.setLayoutX(cellBounds.getMinX()-4.5);
                        shipVersion.setLayoutY(cellBounds.getMinY()-97);
                    } else if (version == 3) {
                        shipVersion.setLayoutX(cellBounds.getMinX()-4.5);
                        shipVersion.setLayoutY(cellBounds.getMinY()-101);
                    } else {
                        shipVersion.setLayoutX(cellBounds.getMinX()-4.5);
                        shipVersion.setLayoutY(cellBounds.getMinY()-106);
                    }
                } else {
                    shipVersion.getTransforms().add(new Rotate(270, pivotX, pivotY));
                    shipVersion.setLayoutX(cellBounds.getMinX()-4.5);

                    if (version == 1) {
                        shipVersion.setLayoutY(cellBounds.getMinY() - 88);
                    } else if (version == 2) {
                        shipVersion.setLayoutY(cellBounds.getMinY() - 92);
                    } else if (version == 3) {
                        shipVersion.setLayoutY(cellBounds.getMinY() - 96);
                    } else {
                        shipVersion.setLayoutY(cellBounds.getMinY() - 103);
                    }
                }

                shipVersion.setDisable(true);
                numShipsPlaced++;
            } else {
                // Reset because it couldn't be placed
                rootPane.getChildren().remove(shipVersion);
                shipPalette.getChildren().remove(shipVersion);
                shipPalette.getChildren().add(shipVersion);
            }

            // RESET
            resetFinalCoords = false;
            shipFinalCoords = new ArrayList<>();
            rootPane.setCursor(Cursor.DEFAULT);
            dragging = false;
            currentShipVisual = null;

            if (rotated) {
                shipVersion.setRotate(0);
                rotated = false;
            }

            for(Node n: playerGrid.getChildren()){
                if(n instanceof StackPane){
                    n.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");
                }
            }

        });

    }


    /**
     * Loads the StackPane cells into the GridPane to manage the ship positions.
     */
    private void drawGrid() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                //StackPane allows to place figures upon it
                StackPane cell = new StackPane();
                cell.setPrefSize(100, 100);
                cell.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");

                playerGrid.add(cell, i, j);

               }

            }


        }


    /**
     * Gets the reference cell from the GridPane to help place the ship when it is released.
     *
     * @param gridPane the container where ships are placed
     * @param row the row of the node to find
     * @param col the column of the node to find
     *
     * @return the Node object inside the GridPane
     */
    public Node getCellPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            // default to 0 if null
            int r = (rowIndex == null) ? 0 : rowIndex;
            int c = (colIndex == null) ? 0 : colIndex;

            if (r == row && c == col) {
                return node;
            }
        }
        return null; // not found

    }


    /**
     * Continues the game after placing all ships.
     * sends  the player to the GameController, updates the plain text files,
     * and creates the serialized files for both boards.
     *
     * @param event action event from the button
     */
    @FXML
    public void handleClickContinue(javafx.event.ActionEvent event) {
        if (numShipsPlaced == 10) {

            GameStage gameStage = new GameStage();
            gameStage.gameControllerStage(); // This loads the game board

            GameController controller = gameStage.getGameController();

            if (controller != null) {

            /* Read the plain text file and update the value of ableToContinue
               (used by the "continue" button in WelcomeStage),
               because at this point the board is ready to play. */
                String content = player.getNickname() + "," + player.getSunkenShips() + "," + "true";
                plainTextFileHandler.writeToFile("player_data.csv", content);

                /* Reset the number of sunken ships for machine_data */
                plainTextFileHandler.writeToFile("machine_data.csv", "machine" + "," + 0 + "," + "true");

                // Serialize board to be used in GameController
                seriazableFileHandler.serialize("player_board.ser", playerBoard);

                Machine machine = new Machine();
                machine.fillBoard(); // fill its board randomly
                seriazableFileHandler.serialize("machine_board.ser", machine.getBoard()); // or just machine.getBoard()

                controller.setPlayer(player);
                controller.startGame();

            } else {
                System.err.println("GameController is null");
            }

        } else {
            System.out.println("you have not placed all the ships yet");
        }
    }




    /**
     * Sets the player object which stores player information.
     *
     * @param player object that stores player data.
     */
    public void setPlayer(Player player){
            this.player = player;
            System.out.println(player.getSunkenShips());
            System.out.println(player.getNickname());
        }


}
