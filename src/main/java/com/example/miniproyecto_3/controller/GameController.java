package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.exceptions.DataLoadException;
import com.example.miniproyecto_3.exceptions.InvalidMoveException;
import com.example.miniproyecto_3.model.*;

import com.example.miniproyecto_3.model.planeSerializableFiles.SeriazableFileHandler;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import com.example.miniproyecto_3.view.Figures;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private PlainTextFileReader plainTextFileReader;
    //imagenes que representan los resultados del disparo.
    private final Image missImage = new Image(getClass().getResourceAsStream("/images/x.png"));
    private final Image hitImage = new Image(getClass().getResourceAsStream("/images/explosión.png"));
    private final Image sunkenShipImage = new Image(getClass().getResourceAsStream("/images/smoke.png"));






    public GameController() {
    }


    public void initialize() {

        this.playerBoard = new Board(10, 10);

        this.seriazableFileHandler = new SeriazableFileHandler();

        this.machine = new Machine(0);

        this.plainTextFileReader = new PlainTextFileReader();

    }


    private void drawGrids() {

        // Mostrar botones o celdas con barcos

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                boolean shipThere = playerBoard.getCell(j-1,i-1).getShip() != null;

                StackPane stackPane = new StackPane();

                //GridPane.setHgrow(button, Priority.ALWAYS);
                //GridPane.setVgrow(button, Priority.ALWAYS);
                stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");

                //if(shipThere)  stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: red;");

                // button.setPrefWidth(10);   // Ancho preferido
                //  button.setPrefHeight(5);

                Button button = new Button();

                //GridPane.setHgrow(button, Priority.ALWAYS);
                //GridPane.setVgrow(button, Priority.ALWAYS);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: lightblue;");


                playerGrid.add(stackPane, i, j);
                mainGrid.add(button, i, j);



               //boton para manejar los disparo

            }
        }

        System.out.println(" ");

    }



    /**
     *  Se inicia la ventana de juego, se dibuja la pantalla luego de que board se halla puesto una vez se termino
     * de colocar en el placement controller.
     * */

    public void startGame() {
        try{
            String[] playerData = plainTextFileReader.readFromFile("player_data.csv");
            if(playerData == null || playerData.length <= 2){
                throw new DataLoadException("Archivo player_data.csv corrupto o vacío");
            }
            player.setSunkenShips(Integer.parseInt(playerData[1]));
            System.out.println("ships sunken by player"+player.getSunkenShips());
            playerLabel.setText("Barcos hundidos por el jugador: "+player.getSunkenShips());

            String[] machineData =  plainTextFileReader.readFromFile("machine_data.csv");
            if(machineData == null || machineData.length <= 2){
                throw new DataLoadException("Archivo machine_data.csv corrupto o vacío");
            }
            System.out.println("ships sunken by machine"+Integer.parseInt(machineData[1]));
            machineLabel.setText("Barcos hundidos por el enemigo: "+Integer.parseInt(machineData[1]));

            Object objPlayer = seriazableFileHandler.deserialize("player_board.ser");
            if(!(objPlayer instanceof Board)){
                throw new DataLoadException("El archivo player_board.ser no contiene un tablero válido");
            }
            playerBoard = (Board) objPlayer;

            Object objMachine = seriazableFileHandler.deserialize("machine_board.ser");
            if(!(objMachine instanceof Board)){
                throw new DataLoadException("El archivo machine_board.ser no contiene un tablero válido");
            }
            machine.setBoard((Board) objMachine);
            machine.getBoard().printCellGrid();
            //playerBoard.printCellGrid();

            drawGrids();
            drawShips(playerBoard,playerGrid, false);
            //drawShips(machine.getBoard(),mainGrid);
        }catch(DataLoadException e){
            System.out.println("Error crítico al cargar datos" + e.getMessage());
        }

        handlePlayerShot();
        //solución para renderizado inicial correcto, evita que no se ven los elementos visuales al abrir la ventana de juego
        Platform.runLater(() -> {
            PauseTransition initialDelay = new PauseTransition(Duration.millis(100));
            initialDelay.setOnFinished(e -> {
                //RESET GRIDS CONTINUE
                updateGridVisuals(machine.getBoard(),mainGrid);
                updateGridVisuals(playerBoard,playerGrid);
                //forzar actualizacion de layout
                mainGrid.requestLayout();
                playerGrid.requestLayout();

                PauseTransition cleanupDelay = new PauseTransition(Duration.millis(50));
                cleanupDelay.setOnFinished(ev -> {
                    resetAuxFlags(playerBoard); // aux para la visualización del tablero
                    resetAuxFlags(machine.getBoard());         // VISUALIZACIÓN TABLERO DEL OPONENTE PROFESOR
                    System.out.println("Visualizacion inicial completada");
                });
                cleanupDelay.play();
            });
            initialDelay.play();
        });

    }

    /**
     * Coloco los barcos usando la primera celda de estos (PODRÍA USAR HERENCIA CÓDIGO SIMILAR MOUSE SET RELEASED)
     * */
    public void drawShips(Board board, GridPane gridPane, boolean cheatMode) {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Ship ship = board.getCell(row, col).getShip();

                Node ref = getCellPane(gridPane, row+1, col+1);

                if (ship != null && !ship.getAux() && ref != null) {

                    ship.setAux(true);

                    Group shipGroup = Figures.createShip(ship.getSize(), Color.GRAY, Color.BLACK);

                    shipGroup.setScaleX(0.78);
                    shipGroup.setScaleY(0.78);

                    if(cheatMode){
                        shipGroup.setId("enemyShip"); //Indentificador para los barcos enemigos
                    }


                    Platform.runLater(() -> {

                        Bounds cellBounds = ref.localToScene(ref.getBoundsInLocal());
                        double pivotX = cellBounds.getWidth() / 2;
                        double pivotY = cellBounds.getHeight() / 2;

                        if(!ship.getOrientation()){
                            if (ship.getSize()==1){
                                shipGroup.setLayoutX(cellBounds.getMinX()-4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY()-32);
                            }
                            else if (ship.getSize()==2) {
                                shipGroup.setLayoutX(cellBounds.getMinX()-4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY()-39);
                            }
                            else if (ship.getSize()==3) {
                                shipGroup.setLayoutX(cellBounds.getMinX()-4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY()-40);
                            }
                            else{
                                shipGroup.setLayoutX(cellBounds.getMinX()-4.5);
                                shipGroup.setLayoutY(cellBounds.getMinY()-42);
                            }


                        }else{
                            shipGroup.getTransforms().add(new Rotate(270, pivotX, pivotY));
                            shipGroup.setLayoutX(cellBounds.getMinX()-4.5);

                            if(ship.getSize() == 1) {
                                shipGroup.setLayoutY(cellBounds.getMinY() - 25);
                            }
                            else if(ship.getSize()==2) {
                                shipGroup.setLayoutY(cellBounds.getMinY()-32);
                            }
                            else if(ship.getSize()==3) {
                                shipGroup.setLayoutY(cellBounds.getMinY()-38);
                            }
                            else{
                                shipGroup.setLayoutY(cellBounds.getMinY()-45);
                            }

                        }
                        playerAnchorPane.getChildren().add(shipGroup);
                    });



                    // Rotar si es vertical



                }
            }
        }
    }




    public void handlePlayerShot() {
        for (Node node : mainGrid.getChildren()) {
            if (node instanceof Button) {
                int row = GridPane.getRowIndex(node) - 1;
                int col = GridPane.getColumnIndex(node) - 1;
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    try {
                        if (row < 0 || row >= 10 || col >= 10 || col < 0) {
                            throw new InvalidMoveException("Coordenadas fuera del tablero");
                        }
                        Cell targetCell = machine.getBoard().getCell(row, col);
                        if (targetCell.isHit()) {
                            saveGame();
                            throw new InvalidMoveException(" Ya disparaste en esta celda");
                        }
                        targetCell.hit();
                        updateGridVisuals(machine.getBoard(), mainGrid);
                        updateSunkenShipsCount(machine.getBoard(), false);
                        node.setDisable(false);
                        saveGame();
                        //  Aquí analizamos si fue acierto o fallo:
                        if (!targetCell.isOccupied()) {
                            // agua pasa el turno a la máquina
                            handleMachineShot();
                            } else {
                                //  Tocado o hundido el jugador sigue, NO hacemos nada (no llamamos a handleMachineShot)
                                System.out.println("¡Tocado o hundido! El jugador sigue.");
                            }

                    }catch (InvalidMoveException ex){
                        System.out.println("Movimiento invalido" + ex.getMessage());
                    }
                });
            }
        }
    }



    public void handleMachineShot() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5)); // 0.5s entre disparos

        pause.setOnFinished(event -> {
            playerBoard = machine.makeMove(playerBoard);
            updateGridVisuals(playerBoard, playerGrid);

            int row = playerBoard.getLastShotRow();
            int col = playerBoard.getLastShotColumn();
            Cell lastShotCell = playerBoard.getCell(row, col);

            if (lastShotCell.isOccupied()) {
                System.out.println("Tocado o hundido por barco machine sigue jugando!");
                System.out.println(row + "," + col);

                updateSunkenShipsCount(playerBoard, true);

                saveGame(); // <--- Guardar después del disparo exitoso

                if (sunkenShipsByMachine < 10) {
                    pause.playFromStart();
                }
            } else {
             //   System.out.println("Máquina falló en " + row + "," + col + ", ahora le toca al jugador.");
                saveGame(); // <--- Guardar después de fallar también
            }
        });

        pause.play();
        saveGame();
    }


    /*
    se actualizan los elementos visuales del gridpane recibido. Se recorre todas las celdas del gridpane y al detectar
    una golpeada, se superpone una imagen dependiendo del resultado del disparo: fallido, acertado y abarco hundido.
     */

    public void updateGridVisuals(Board board, GridPane gridPane) {
        //elimina los imagenes que esten asociadas al gridpane, para evitar elementos duplicados
        playerAnchorPane.getChildren().removeIf(node -> {
            if(node instanceof  Group){
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
                    //calculo de la posicion para la imagen
                    Bounds cellBounds = node.localToScene(node.getBoundsInLocal());
                    Point2D anchorPoint = playerAnchorPane.sceneToLocal(cellBounds.getMinX(), cellBounds.getMinY());
                    //se crea la imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    imageView.setMouseTransparent(true);
                    //se crea el rectangulo que va a contener la imagen
                    Rectangle rectangle = new Rectangle(40,40);
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.TRANSPARENT);
                    rectangle.setMouseTransparent(true);
                    //se asigna la imagen correcta
                    if (!cell.isOccupied()) {
                        node.setStyle("-fx-background-color: salmon;");
                        imageView.setImage(missImage);

                    } else {
                        Ship ship = cell.getShip();
                        if (ship.getLifes() > 0) {
                            node.setStyle("-fx-background-color: lightgreen;");
                            imageView.setImage(hitImage);
                        } else {
                            node.setStyle("-fx-background-color: green;");
                            imageView.setImage(sunkenShipImage);

                        }
                    }
                    //se agrupa la imagen con el rectangulo
                    Group graphicContainer = new Group(rectangle, imageView);
                    //posicionamiento
                    graphicContainer.setLayoutX(anchorPoint.getX() + (cellBounds.getWidth()-40)/2);
                    graphicContainer.setLayoutY(anchorPoint.getY() + (cellBounds.getHeight() - 40)/2);
                    //se marca el group como perteneciente al gridpane actual, usando los propiedades arbitrarias en los nodos
                    graphicContainer.getProperties().put("associatedGrid",gridPane);
                    graphicContainer.setMouseTransparent(true);
                    //se añade el group al anchorpane
                    playerAnchorPane.getChildren().add(graphicContainer);
                }
            }
        }
    }




    /**
     * Sí un barco se hundió actualizo la cantidad de barcos hundidos por player y por machine
     * */
    public void updateSunkenShipsCount(Board board, boolean isPlayerBoard) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = board.getCell(i, j);
                if (cell.isHit() && cell.isOccupied()) {
                    Ship ship = cell.getShip();
                    if (ship.getLifes() == 0 && !ship.isAlreadyCounted()) {
                        ship.setAlreadyCounted(true);
                        if (!isPlayerBoard) {

                            sunkenShipsByPlayer = Integer.parseInt(plainTextFileReader.readFromFile("player_data.csv")[1]);
                            sunkenShipsByPlayer++;
                            System.out.println("Sunken ships by player: " + sunkenShipsByPlayer);
                            playerLabel.setText("Barcos hundidos por jugador: " + sunkenShipsByPlayer);

                            // Actualizar archivo plano
                            String[] data = plainTextFileReader.readFromFile("player_data.csv");

                            data[1] = String.valueOf(sunkenShipsByPlayer); // actualizamos solo la cantidad
                            String newContent = data[0] + "," + data[1] + "," + "true";
                            plainTextFileReader.writeToFile("player_data.csv", newContent);

                        } else {
                            // Leer y actualizar archivo de la máquina
                            sunkenShipsByMachine = Integer.parseInt(plainTextFileReader.readFromFile("machine_data.csv")[1]);
                            sunkenShipsByMachine++;
                            System.out.println("Sunken ships by machine: " + sunkenShipsByMachine);
                            machineLabel.setText("Barcos hundidos por el enemigo: " + sunkenShipsByMachine);

                            String[] data = plainTextFileReader.readFromFile("machine_data.csv");

                            if (data.length >= 2) {
                                data[1] = String.valueOf(sunkenShipsByMachine);
                            } else {
                                // En caso de que esté vacío o mal formateado
                                data = new String[]{"machine", String.valueOf(sunkenShipsByMachine), "true"};
                            }

                            String newContent = data[0] + "," + data[1] + "," + data[2];
                            plainTextFileReader.writeToFile("machine_data.csv", newContent);
                        }
                    }
                }
            }
        }

        // Verificar si alguien ganó
        if (sunkenShipsByPlayer == 10) {
            System.out.println("¡Ganaste!");
            endGame("¡Ganaste!");
        } else if (sunkenShipsByMachine == 10) {
            System.out.println("La máquina ganó...");
            endGame("La máquina ganó...");
        }

    }

    public void endGame(String message) {
        // Cambiar el campo de "puede seguir jugando" a false
        String[] playerData = plainTextFileReader.readFromFile("player_data.csv");
        if (playerData.length >= 3) {
            playerData[2] = "false";
            String newContent = " " + "," + 0 + "," + playerData[2];
            plainTextFileReader.writeToFile("player_data.csv", newContent);
        }

        disableGameControls();
        // Desactivar todos los botones del tablero enemigo
        for (Node node : mainGrid.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        }

        // También puedes hacer esto si quieres cerrar la app:
        // Platform.exit();

        // Mostrar alerta al usuario
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Fin del juego");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void disableGameControls() {
        mainGrid.setDisable(true);
        playerGrid.setDisable(true);
        cheatButton.setDisable(true);
    }

    /**
     *  Reset aux después de que se colocaron visualmente los barcos
     * */
    public void resetAuxFlags(Board board) {
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
     * Serializo los tableros después de un cambio.
     * */
    public void saveGame() {
        seriazableFileHandler.serialize("player_board.ser", playerBoard);
        seriazableFileHandler.serialize("machine_board.ser", machine.getBoard());
        //System.out.println("Juego guardado al cerrar la ventana.");
    }


    /**
     * Coloco el player ya sea desde el controlador placement o si ya había una partida, coloco el player con los datos
     * guardados (nombre y barcos hundidos)
     * */
    public void setPlayer(Player player) {
        this.player = player;
        System.out.println(player.getNickname());
    }


    /**
     * Obtengo la celda de referencia para colocar el barco una vez se soltó. (HACER HERENCIA)
     * */
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
    //funcion para mostrar los barcos del enemigo cuando se presiona el botón "Mostrar tablero"
    @FXML
    private void cheatButtonPressed(){
        resetAuxFlags(machine.getBoard());
        drawShips(machine.getBoard(), mainGrid,true);
    }

    //Cuando se deja de presionar el boton "Mostrar Tablero", se elimina la visualización de los barcos enemigos
    @FXML
    private void cheatButtonReleased(){
        playerAnchorPane.getChildren().removeIf(node -> "enemyShip".equals(node.getId()));
    }




}



