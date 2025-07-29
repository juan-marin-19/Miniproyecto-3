package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.exceptions.DataLoadException;
import com.example.miniproyecto_3.exceptions.InvalidMoveException;
import com.example.miniproyecto_3.model.*;

import com.example.miniproyecto_3.model.planeSerializableFiles.SeriazableFileHandler;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileHandler;
import com.example.miniproyecto_3.view.Figures;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/**
 * Class that controls the game window.
 */
public class GameController {

    @FXML
    private GridPane playerGrid;
    @FXML
    private GridPane mainGrid;
    @FXML
    private AnchorPane playerAnchorPane;
    @FXML
    private Button cheatButton;
    @FXML
    private Label playerLabel;
    @FXML
    private Label machineLabel;

    private Player player;
    private Machine machine;
    private int sunkenShipsByPlayer = 0;
    private int sunkenShipsByMachine = 0;
    private Board playerBoard;
    private SeriazableFileHandler seriazableFileHandler;
    private PlainTextFileHandler plainTextFileHandler;

    // Images representing the results of the shot.
    private final Image missImage = new Image(getClass().getResourceAsStream("/images/x.png"));
    private final Image hitImage = new Image(getClass().getResourceAsStream("/images/explosión.png"));
    private final Image sunkenShipImage = new Image(getClass().getResourceAsStream("/images/smoke.png"));

    /**
     * Empty constructor.
     */
    public GameController() {
    }


    /**
     * Initializes the boards and the objects that manage the files.
     */
    public void initialize() {

       // cheatButton.setVisible(false); // Verification button

        playerAnchorPane.setMinHeight(572);  // or the fixed value you want
        playerAnchorPane.setMaxHeight(572);

        this.playerBoard = new Board(10, 10);

        this.seriazableFileHandler = new SeriazableFileHandler();

        this.machine = new Machine();

        this.plainTextFileHandler = new PlainTextFileHandler();
    }


    /**
     * Starts the game window, draws the screen, deserializes the boards,
     * and calls the method that handles the players shot.
     *
     * @see #drawGrids()
     * @see #drawShips(Board, GridPane, boolean)
     * @see #resetAux(Board)
     * @see #handlePlayerShot()
     * @see #updateGridVisuals(Board, GridPane)
     */
    public void startGame() {
        try {
            String[] playerData = plainTextFileHandler.readFromFile("player_data.csv");
            if (playerData == null || playerData.length <= 2) {
                throw new DataLoadException("File player_data.csv is corrupted or empty");
            }
            player.setSunkenShips(Integer.parseInt(playerData[1]));
            playerLabel.setText("Ships sunk by the player: " + player.getSunkenShips());

            String[] machineData = plainTextFileHandler.readFromFile("machine_data.csv");
            if (machineData == null || machineData.length <= 2) {
                throw new DataLoadException("File machine_data.csv is corrupted or empty");
            }
            machineLabel.setText("Ships sunk by the enemy: " + Integer.parseInt(machineData[1]));

            Object objPlayer = seriazableFileHandler.deserialize("player_board.ser");
            if (!(objPlayer instanceof Board)) {
                throw new DataLoadException("The file player_board.ser does not contain a valid board");
            }
            playerBoard = (Board) objPlayer;

            Object objMachine = seriazableFileHandler.deserialize("machine_board.ser");
            if (!(objMachine instanceof Board)) {
                throw new DataLoadException("The file machine_board.ser does not contain a valid board");
            }
            machine.setBoard((Board) objMachine);
            machine.getBoard().printCellGrid();

            drawGrids();
            drawShips(playerBoard, playerGrid, false);

        } catch (DataLoadException e) {
            System.out.println("Critical error loading data: " + e.getMessage());
        }

        handlePlayerShot();

        // Solution for correct initial rendering, avoids elements not appearing visually when opening the game window
        Platform.runLater(() -> {
            PauseTransition initialDelay = new PauseTransition(Duration.millis(100));
            initialDelay.setOnFinished(e -> {
                // RESET GRIDS CONTINUE
                updateGridVisuals(machine.getBoard(), mainGrid);
                updateGridVisuals(playerBoard, playerGrid);

                mainGrid.requestLayout();
                playerGrid.requestLayout();

                PauseTransition cleanupDelay = new PauseTransition(Duration.millis(50));
                cleanupDelay.setOnFinished(ev -> {
                    resetAux(playerBoard); // aux for player board visualization
                    resetAux(machine.getBoard()); // aux for opponent board visualization (used by instructor)
                });
                cleanupDelay.play();
            });
            initialDelay.play();
        });
    }


    /**
     * Visually places the ships on the GridPane using their first cell as reference.
     *
     * @param board board of the player or the machine
     * @param gridPane container of the board (position or main)
     * @param cheatMode boolean to know if the opponents ships can be shown
     *                  
     * @see #getCellPane(GridPane, int, int)
     */
    public void drawShips(Board board, GridPane gridPane, boolean cheatMode) {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Ship ship = board.getCell(row, col).getShip();

                Node ref = getCellPane(gridPane, row + 1, col + 1);

                if (ship != null && !ship.getAux() && ref != null) {

                    ship.setAux(true);

                    Group shipGroup = Figures.createShip(ship.getSize(), Color.GRAY, Color.BLACK);

                    shipGroup.setScaleX(0.78);
                    shipGroup.setScaleY(0.78);

                    if (cheatMode) {
                        shipGroup.setId("enemyShip"); // Identifier for enemy ships
                    }

                    Platform.runLater(() -> {

                        Bounds cellBounds = ref.localToScene(ref.getBoundsInLocal());
                        double pivotX = cellBounds.getWidth() / 2;
                        double pivotY = cellBounds.getHeight() / 2;

                        if (!ship.getOrientation()) {
                            if (ship.getSize() == 1) {
                                shipGroup.setLayoutX(cellBounds.getMinX() - 4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY() - 32);
                            } else if (ship.getSize() == 2) {
                                shipGroup.setLayoutX(cellBounds.getMinX() - 4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY() - 39);
                            } else if (ship.getSize() == 3) {
                                shipGroup.setLayoutX(cellBounds.getMinX() - 4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY() - 40);
                            } else {
                                shipGroup.setLayoutX(cellBounds.getMinX() - 4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY() - 42);
                            }

                        } else {
                            shipGroup.getTransforms().add(new Rotate(270, pivotX, pivotY));
                            shipGroup.setLayoutX(cellBounds.getMinX() - 4.5);

                            if (ship.getSize() == 1) {
                                shipGroup.setLayoutY(cellBounds.getMinY() - 25);
                            } else if (ship.getSize() == 2) {
                                shipGroup.setLayoutY(cellBounds.getMinY() - 32);
                            } else if (ship.getSize() == 3) {
                                shipGroup.setLayoutY(cellBounds.getMinY() - 38);
                            } else {
                                shipGroup.setLayoutY(cellBounds.getMinY() - 45);
                            }

                        }
                        playerAnchorPane.getChildren().add(shipGroup);
                    });

                    // Rotate if vertical

                }
            }
        }
    }


    /**
     * Places buttons and stackPanes on the machines and players gridPane
     * to manage the game visually through them
     */
    private void drawGrids() {

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                StackPane stackPane = new StackPane();
                stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");

                Button button = new Button();

                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");

                playerGrid.add(stackPane, i, j);
                mainGrid.add(button, i, j);
            }
        }

        System.out.println(" ");
    }


    /**
     * Method with an event handler that checks if a cell was clicked and acts accordingly,
     * handling the players shot, if it hits a ship, destroys it or if it hits nothing, machine will shoot.
     *
     * @see #handleMachineShot()
     * @see #updateGridVisuals(Board, GridPane)
     * @see #updateSunkenShipsCount(Board, boolean)
     * @see #saveGame()
     */
    public void handlePlayerShot() {
        for (Node node : mainGrid.getChildren()) {
            if (node instanceof Button) {
                int row = GridPane.getRowIndex(node) - 1;
                int col = GridPane.getColumnIndex(node) - 1;
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    try {
                        if (row < 0 || row >= 10 || col >= 10 || col < 0) {
                            throw new InvalidMoveException("Coordinates out of board");
                        }
                        Cell targetCell = machine.getBoard().getCell(row, col);

                        if (targetCell.isHit()) {
                            saveGame();
                            throw new InvalidMoveException(" You already shot this cell");
                        }

                        targetCell.hit();
                        updateGridVisuals(machine.getBoard(), mainGrid);
                        updateSunkenShipsCount(machine.getBoard(), false);
                        node.setDisable(false);
                        saveGame();

                        // Analyze whether it's a hit or miss:
                        if (!targetCell.isOccupied()) {
                            handleMachineShot(); // If it's a hit or sunk, player continues (no machine shot)
                        }

                    } catch (InvalidMoveException ex) {
                        System.out.println("Invalid move: " + ex.getMessage());
                    }
                });
            }
        }
    }


    /**
     * Code where the machine makes a shot using its board. If it hits or sinks a ship, it will shoot again.
     *
     * @see #updateGridVisuals(Board, GridPane)
     * @see #updateSunkenShipsCount(Board, boolean)
     * @see #saveGame()
     */
    public void handleMachineShot() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5)); // 0.5s between shots

        pause.setOnFinished(event -> {
            playerBoard = machine.makeMove(playerBoard);
            updateGridVisuals(playerBoard, playerGrid);

            Cell lastShotCell = playerBoard.getCell(playerBoard.getLastShotRow(), playerBoard.getLastShotColumn());

            if (lastShotCell.isOccupied()) {
                System.out.println("Hit or sunk by machine, it keeps shooting!");
                updateSunkenShipsCount(playerBoard, true);
                saveGame(); // Save after successful shot

                if (sunkenShipsByMachine < 10) {
                    pause.playFromStart();
                }

            } else {
                saveGame(); // Save after miss too
            }
        });

        pause.play();
        saveGame();
    }


    /**
     * Updates the visual elements of the gridPane using the given board.
     *
     * @param board the board of the player or the machine
     * @param gridPane the container of the grid (main or position board)
     */
    public void updateGridVisuals(Board board, GridPane gridPane) {

        playerAnchorPane.getChildren().removeIf(node -> {
            if (node instanceof Group) {
                Object gridPaneProperty = node.getProperties().get("associatedGrid");
                return gridPaneProperty != null && gridPaneProperty.equals(gridPane);
            }
            return false;
        });

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button || node instanceof StackPane) {
                int row = GridPane.getRowIndex(node) - 1;
                int col = GridPane.getColumnIndex(node) - 1;

                Cell cell = board.getCell(row, col);

                if (cell.isHit()) {

                    node.setDisable(false);
                    Bounds cellBounds = node.localToScene(node.getBoundsInLocal());
                    Point2D anchorPoint = playerAnchorPane.sceneToLocal(cellBounds.getMinX(), cellBounds.getMinY());

                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    imageView.setMouseTransparent(true);
                    Rectangle rectangle = new Rectangle(40, 40);
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.TRANSPARENT);
                    rectangle.setMouseTransparent(true);

                    if (!cell.isOccupied()) {
                        node.setStyle("-fx-background-color: salmon;");
                        imageView.setImage(missImage);
                    } else {
                        Ship ship = cell.getShip();
                        if (ship.getLives() > 0) {
                            node.setStyle("-fx-background-color: lightgreen;");
                            imageView.setImage(hitImage);
                        } else {
                            node.setStyle("-fx-background-color: green;");
                            imageView.setImage(sunkenShipImage);
                        }
                    }

                    Group graphicContainer = new Group(rectangle, imageView);

                    graphicContainer.setLayoutX(anchorPoint.getX() + (cellBounds.getWidth() - 40) / 2);
                    graphicContainer.setLayoutY(anchorPoint.getY() + (cellBounds.getHeight() - 40) / 2);
                    graphicContainer.getProperties().put("associatedGrid", gridPane);
                    graphicContainer.setMouseTransparent(true);

                    playerAnchorPane.getChildren().add(graphicContainer);
                }
            }
        }
    }




    /**
     * If a ship is sunken, updates the number of sunken ships by player or machine.
     * Also updates the labels and the content of the plain text files
     *
     * @param board the board of the player or the machine
     * @param isPlayerBoard boolean to know which board it is
     *
     * @see #endGame(String)
     */
    public void updateSunkenShipsCount(Board board, boolean isPlayerBoard) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = board.getCell(i, j);
                if (cell.isHit() && cell.isOccupied()) {
                    Ship ship = cell.getShip();
                    if (ship.getLives() == 0 && !ship.isAlreadyCounted()) {
                        ship.setAlreadyCounted(true);
                        if (!isPlayerBoard) {

                            sunkenShipsByPlayer = Integer.parseInt(plainTextFileHandler.readFromFile("player_data.csv")[1]);
                            sunkenShipsByPlayer++;
                            playerLabel.setText("Ships sunk by the player: " + sunkenShipsByPlayer);

                            // Update plain text file
                            String[] data = plainTextFileHandler.readFromFile("player_data.csv");
                            data[1] = String.valueOf(sunkenShipsByPlayer); // update only the count
                            String newContent = data[0] + "," + data[1] + "," + "true";
                            plainTextFileHandler.writeToFile("player_data.csv", newContent);

                        } else {
                            // Read and update machine file
                            sunkenShipsByMachine = Integer.parseInt(plainTextFileHandler.readFromFile("machine_data.csv")[1]);
                            sunkenShipsByMachine++;
                            machineLabel.setText("Ships sunk by the enemy: " + sunkenShipsByMachine);

                            String[] data = plainTextFileHandler.readFromFile("machine_data.csv");

                            if (data.length >= 2) {
                                data[1] = String.valueOf(sunkenShipsByMachine);
                            } else {
                                data = new String[]{"machine", String.valueOf(sunkenShipsByMachine), "true"};
                            }

                            String newContent = data[0] + "," + data[1] + "," + data[2];
                            plainTextFileHandler.writeToFile("machine_data.csv", newContent);
                        }
                    }
                }
            }
        }

        // Check if someone won
        if (sunkenShipsByPlayer == 10) {
            endGame("You won!");
        } else if (sunkenShipsByMachine == 10) {
            endGame("The machine won...");
        }
    }


    /**
     * Handles the end of the game, disables functions, shows a final message, and updates a plain text file.
     *
     * @param message message to show in an alert window
     */
    public void endGame(String message) {

        String[] playerData = plainTextFileHandler.readFromFile("player_data.csv");
        if (playerData.length >= 3) {
            playerData[2] = "false";
            String newContent = " " + "," + 0 + "," + playerData[2];
            plainTextFileHandler.writeToFile("player_data.csv", newContent);
        }

        mainGrid.setDisable(true);
        playerGrid.setDisable(true);
        cheatButton.setDisable(true);

        for (Node node : mainGrid.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        }

        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


    /**
     * Resets the auxiliary boolean variables used to know if a ship was already shown on the board.
     * Used for when the window is closed and opened again.
     *
     * @param board the board of the player or the machine
     */
    public void resetAux(Board board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Ship ship = board.getCell(i, j).getShip();
                if (ship != null) {
                    ship.setAux(false);
                }
            }
        }
    }


    /**
     * Serializes the boards after a change.
     */
    public void saveGame() {
        seriazableFileHandler.serialize("player_board.ser", playerBoard);
        seriazableFileHandler.serialize("machine_board.ser", machine.getBoard());
    }


    /**
     * Setter for the player object, which stores the player's information.
     *
     * @param player object that stores the player info
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Same method as in PlacementController
     * Gets the cell from the gridPane at a specific row and column.
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
     * Method to show the enemy ships when the "Show Board" button is pressed.
     *
     * @see #resetAux(Board)
     */
    @FXML
    private void cheatButtonPressed() {
        resetAux(machine.getBoard());
        drawShips(machine.getBoard(), mainGrid, true);
    }


    /**
     * Method to remove the enemy ships when the "Show Board" button is released.
     */
    @FXML
    private void cheatButtonReleased() {
        playerAnchorPane.getChildren().removeIf(node -> "enemyShip".equals(node.getId()));
    }




}



