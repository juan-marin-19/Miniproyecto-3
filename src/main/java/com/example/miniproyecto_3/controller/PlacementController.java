package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;
import com.example.miniproyecto_3.model.planeTextFiles.PlainTextFileReader;
import com.example.miniproyecto_3.view.Figures;
import com.example.miniproyecto_3.view.GameStage;
import com.example.miniproyecto_3.view.WelcomeStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PlacementController {

    private Player player;
    private boolean dragging = false;
    private boolean rotated = false; // rotated; vertical = false ; horizontal = true
    private boolean resetFinalCoords = false;
    private Board playerBoard;
    private Group currentShipVisual = null;  // referencia de versionShip, para manejar el cambio de orientación
    private List<int[]> shipFinalCoords = new ArrayList<>(); // lista para guardar las coordenadas correctas de un ship
    private int numShipsPlaced = 0;
    private PlainTextFileReader plainTextFileReader;


    @FXML
    private AnchorPane rootPane; // AnchorPane contenedor de grid y shipPallete

    @FXML
    private GridPane playerGrid;  // grid para los barcos

    @FXML
    private FlowPane shipPalette; // Contenedor para los barcos

    @FXML
    private BorderPane borderPane;  // contenedor de todos


    public PlacementController() {
        this.plainTextFileReader = new PlainTextFileReader();

    }


    /**
     * CARGO las figuras, y las funciones para el movimiento, dibujo de todo , y los clicks
     */
    public void initialize() {
        this.playerBoard = new Board(10, 10);

        //FIJA LA PANTALLA PARA QUE NO SE VAYA HACIA ABAJO
        rootPane.setMinHeight(400);
        rootPane.setMaxHeight(400);
        rootPane.setPrefHeight(400);


        //CÓDIGO PARA MANEJAR EL CAMBIO DE ORIENTACIÓN CON LA LETRA G
        borderPane.setOnKeyPressed(event -> {
            if (dragging && event.getCode() == KeyCode.G && currentShipVisual != null) {
                if (!rotated) {
                    currentShipVisual.setRotate(270);
                    rotated = true;
                    for (Node node : playerGrid.getChildren()) {
                        if (node instanceof StackPane) {
                            node.setStyle("-fx-border-color: black; -fx-background-color: white;");
                        }
                    }
                } else {
                    currentShipVisual.setRotate(0);
                    rotated = false;
                    for (Node node : playerGrid.getChildren()) {
                        if (node instanceof StackPane) {
                            node.setStyle("-fx-border-color: black; -fx-background-color: white;");
                        }
                    }
                }
            }
        });


        // creo las figuras y las añado a una lista de figuras para poder quitarlas luego
        // version 1 = fragata, 2= destructor
        Group fragata1 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata2 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata3 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group fragata4 = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK);
        Group destructor1 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group destructor2 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group destructor3 = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK);
        Group submarine1 = Figures.Submarine(Color.LIGHTBLUE, Color.BLACK);
        Group submarine2 = Figures.Submarine(Color.LIGHTBLUE, Color.BLACK);
        Group portaaviones = Figures.Portaaviones(Color.LIGHTBLUE, Color.BLACK);
        //Rectangle rect = new Rectangle(0, 0, 42, 183);
       // Group rectGroup = new Group(rect);

        // Dibujar el grid añadiendo stackPanes en cada celda del playerGrid
        drawBoard();

        //cargo las figuras y el código de su movimiento, (version = size)

        loadShipPalette(fragata1, 1);
        loadShipPalette(fragata2, 1);
        loadShipPalette(fragata3, 1);
        loadShipPalette(fragata4, 1);
        loadShipPalette(destructor1, 2);
        loadShipPalette(destructor2, 2);
        loadShipPalette(destructor3, 2);
        loadShipPalette(submarine1, 3);
        loadShipPalette(submarine2, 3 );
        loadShipPalette(portaaviones, 4);


    }




    /**
     * NUEVO METODO
     */
    private void loadShipPalette(Group shipVersion,int version){

        //escalamos y colocamos el barco en la paleta de barcos
        shipVersion.setScaleX(0.78);
        shipVersion.setScaleY(0.78);

        shipPalette.getChildren().add(shipVersion);


        // Guardamos la posición original para poder "volver" si es necesario

        final List<StackPane> previouslyHighlighted = new ArrayList<>();


        //PRESIONO UN SHIP
        shipVersion.setOnMousePressed(event -> {

            //ajustar la orientación visual
            dragging = true;
            currentShipVisual = shipVersion;


            // Mover el barco al root del flowPane al anchorPane para que se vea encima de todo

            Bounds shipBounds = shipVersion.localToScene(shipVersion.getBoundsInLocal());

            shipPalette.getChildren().remove(shipVersion);
            rootPane.getChildren().add(shipVersion);

            // Colocar el barco en la posición donde fue presionado (relativo a la escena)
            shipVersion.setLayoutX(shipBounds.getMinX());
            shipVersion.setLayoutY(shipBounds.getMinY());
        });


        //MOVIMIENTO SOSTENIDO (AQUÍ SE COLOCA EL BARCO)
        shipVersion.setOnMouseDragged(event -> {

            shipVersion.toFront();         // lo pone encima de los demás

            //Ocultamos el cursor
            rootPane.setCursor(Cursor.NONE);

            // Obtener el centro del barco en coordenadas de escena
            Bounds shipBounds = shipVersion.localToScene(shipVersion.getBoundsInLocal());

            //Encontramos la orientación actual del barco
            boolean isVertical = !(Math.abs(shipVersion.getRotate() % 180) == 90);


            // Actualizar posición del barco al moverlo, visualmente se mueve aproximadamente desde el centro y
            // --- Ajuste visual del barco según orientación ---
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


            // Limpiar celdas anteriores
            for (StackPane cell : previouslyHighlighted) {
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
            }
            previouslyHighlighted.clear();


            double referencePointX;
            double referencePointY;

            //calculo la posición de referencia para calcular los cuadros que ocupa el barco
            if (isVertical) {
                if (version == 1) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 1.96;
                } else if (version == 3) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.3;
                } else if (version == 4) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;// 2.416
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.49;
                } else {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2.1;
                }
            } else {
                if (version == 1) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 1.96;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else if (version == 3) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.3;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else if (version == 4) {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.49;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                } else {
                    referencePointX = (shipBounds.getMinX() + shipBounds.getMaxX()) / 2.1;
                    referencePointY = (shipBounds.getMinY() + shipBounds.getMaxY()) / 2;
                }
            }




            // CÓDIGO PARA COLOCAR LA VISTA DE DONDE SE COLOCA EL BARCO Y PARA AÑADIRLO AL MODELO Y LUEGO HACER QUE SE POSICIONE
            for (Node node : playerGrid.getChildren()) {
                if (node instanceof StackPane) {
                    Bounds cellBounds = node.localToScene(node.getBoundsInLocal());

                    //obtengo la col y row de la celda que contiene el punto de referencia
                    if (cellBounds.contains(referencePointX, referencePointY)) {
                        Integer col = GridPane.getColumnIndex(node);
                        Integer row = GridPane.getRowIndex(node);

                        // Evitar null (puede pasar en celdas sin set explícito)
                        if (col == null) col = 0;
                        if (row == null) row = 0;

                        // Obtener coordenadas desde el modelo de los cuadros que ocupan el barco (se guardan en un array 2d de int)
                        List<int[]> shipCoords = playerBoard.getCoordinatesForShip(row, col, version, isVertical);
                        //guardarlo para usarlo luego si no está vacío es decir, si encuentra lugar
                        shipFinalCoords = shipCoords;
                        //veo si tengo que resetear las coordenadas por si no encuentra las encuentra por estar fuera de rango o ya haber un barco
                        resetFinalCoords = shipCoords.isEmpty();
                        // Verificar si hay espacio suficiente y encontrar las casillas posición para pintar
                        if (!shipCoords.isEmpty()) {
                            for (int[] coord : shipCoords) {
                                int r = coord[0];
                                int c = coord[1];

                                for (Node n : playerGrid.getChildren()) {
                                    if (n instanceof StackPane) {
                                        Integer nc = GridPane.getColumnIndex(n);
                                        Integer nr = GridPane.getRowIndex(n);
                                        if (nc != null && nr != null && nc == c && nr == r) {
                                            n.setStyle("-fx-border-color: black; -fx-background-color: lightgreen;");
                                            previouslyHighlighted.add((StackPane) n);
                                        }
                                    }
                                }
                            }
                        }
                        break; // Ya encontramos la celda central
                    }else{
                        resetFinalCoords = true;
                    }
                }
            }

            event.consume();
        });


        //SUELTO UN SHIP
        shipVersion.setOnMouseReleased(event -> {

            if(resetFinalCoords){
                shipFinalCoords = new ArrayList<>();
            }
            // Por ahora, siempre vuelve a la paleta


            if(!shipFinalCoords.isEmpty()) {
                // El arreglo no tiene nada entonces hay que colocar el barco donde nos indica las coordenadas
                //  rootPane.getChildren().remove(shipVersion);
                //añado el barco a el arreglo de cell en el modelo board.
                playerBoard.placeShip(shipFinalCoords,rotated);

                //add the ship to the grid visually
                System.out.println(shipFinalCoords.get(0)[0] + " " + shipFinalCoords.get(0)[1]);

                Node referenceCell = getCellPane(playerGrid,shipFinalCoords.get(0)[0] , shipFinalCoords.get(0)[1]);
                Bounds cellBounds = referenceCell.localToScene(referenceCell.getBoundsInLocal());
                double pivotX = cellBounds.getWidth() / 2;
                double pivotY = cellBounds.getHeight() / 2;

                if(!rotated){
                    shipVersion.setLayoutX(cellBounds.getMinX()-4.5);
                    shipVersion.setLayoutY(cellBounds.getMinY()-39);
                }else{
                    shipVersion.getTransforms().add(new Rotate(270, pivotX, pivotY));
                    shipVersion.setLayoutX(cellBounds.getMinX()-4.5);

                    if(version==3) {
                        shipVersion.setLayoutY(cellBounds.getMinY()-35);
                    }else if(version == 1) {
                        shipVersion.setLayoutY(cellBounds.getMinY() - 25);
                    }
                    else if(version==2) {
                        shipVersion.setLayoutY(cellBounds.getMinY()-30);
                    }else{
                        shipVersion.setLayoutY(cellBounds.getMinY()-40);
                    }

                }

                shipVersion.setDisable(true);
                numShipsPlaced++;
            }
            else{
                //resets because it couldn't be placed
                rootPane.getChildren().remove(shipVersion);
                shipPalette.getChildren().remove(shipVersion); // por si quedó por ahí
                shipPalette.getChildren().add(shipVersion);
            }





            // RESET
            resetFinalCoords = false;

            shipFinalCoords = new ArrayList<>();

            rootPane.setCursor(Cursor.DEFAULT);

            for(Node node: playerGrid.getChildren()){
                if(node instanceof StackPane){
                    node.setStyle("-fx-border-color: black; -fx-background-color: white;");
                }
            }


            //devolver barco orientación vertical
            dragging = false;
            currentShipVisual = null;

            if (rotated) {
                // Devuelve a vertical
                shipVersion.setRotate(0);
                rotated = false;
            }


        });


    }


    /**
     * NUEVO METODO
     * */
    private void drawBoard() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                // agrega un stackpane para poder superponer los barcos
                StackPane cell = new StackPane();
                cell.setPrefSize(100, 100);
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");

                playerGrid.add(cell, i, j);

               }

            }


        }



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



    @FXML
    public void handleClickContinue(javafx.event.ActionEvent event){
        if(numShipsPlaced==10){
            // Aquí ya se colocaron los barcos
            Board boardListo = playerBoard; // o como tengas el board final
            Player player = this.player; // el jugador

            GameStage gameStage = new GameStage();
            gameStage.GameStage1(); // Esto carga el tablero de juego

            GameController controller = gameStage.getGameController();
            if (controller != null) {
                controller.setBoard(boardListo);
                System.out.println("Board enviado al controlador de juego");
            } else {
                System.err.println("GameController es null");
            }
        } else {
            System.out.println("aún no has colocado todos los barcos");
        }

    }


    public void startPlay (Player player){
            this.player = player;
            System.out.println(player.getSunkenShips());
            System.out.println(player.getNickname());
        }


}
