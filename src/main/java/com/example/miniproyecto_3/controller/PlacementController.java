package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;
import com.example.miniproyecto_3.view.Figures;
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

import java.util.ArrayList;
import java.util.List;


public class PlacementController {

    private Player player;
    private boolean dragging = false;
    private boolean rotated = false; // rotated; vertical = false ; horizontal = true
    private Board playerBoard;
    public int currentShipId;
    private Group currentShipVisual = null;
    private List<int[]> shipFinalCoords;



    @FXML
    private AnchorPane rootPane; // AnchorPane contenedor de grid y shipPallete

    @FXML
    private GridPane playerGrid;  // grid para los barcos

    @FXML
    private FlowPane shipPalette; // Contenedor para los barcos

    @FXML
    private BorderPane borderPane;  // contenedor de todos


    public PlacementController() {

    }


    /**
     * CARGO las figuras, y las funciones para el movimiento, dibujo de todo , y los clicks
     */
    public void initialize() {
        this.playerBoard = new Board(10, 10);


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


        // Dibujar el grid añadiendo stackPanes en cada celda del playerGrid
        drawBoard();


        //cargo las figuras y el código de su movimiento, (version = size)

        loadShipPalette(fragata1, 1, 0);
        loadShipPalette(fragata2, 1, 1);
        loadShipPalette(fragata3, 1, 2);
        loadShipPalette(fragata4, 1, 3);
        loadShipPalette(destructor1, 2, 4);
        loadShipPalette(destructor2, 2, 5);
        loadShipPalette(destructor3, 2, 6);
        loadShipPalette(submarine1, 3, 7);
        loadShipPalette(submarine2, 3, 8);
        loadShipPalette(portaaviones, 4, 9);


        Platform.runLater(() -> {
            for (Node node : playerGrid.getChildren()) {
                if (node instanceof StackPane) {
                    Bounds bounds = node.getBoundsInParent();
                    System.out.println("StackPane width: " + bounds.getWidth());
                    System.out.println("StackPane height: " + bounds.getHeight());
                    break;
                }
            }
        });

    }


    /**
    * MOVIMIENTO Y COLOCACIÓN DEL BARCO  ,  (me falta que el mouse este en el centro , no aparezca)
     *
     * METODO FUNCIONAL!!!!!!
    */
    /*
    private void loadShipPalette(Group shipVersion,int version, int currentShipId) {

            shipVersion.setUserData(new Ship(version, currentOrientation));

            //empieza el arrastre cuando se toca la figura
            shipVersion.setOnMousePressed(e -> {

                this.currentShipId = currentShipId;

                if (currentShip != null && floatingShip != null) {
                    // Ya hay un barco activo: cancelar selección
                    rootPane.getChildren().remove(floatingShip);
                    rootPane.setOnMouseMoved(null);
                    floatingShip = null;
                    currentShip = null;
                    System.out.println("Selección cancelada");
                    //rootPane.setCursor(Cursor.DEFAULT);
                    return;

                }

                //desaparece cursor cuando undo
                //rootPane.setCursor(Cursor.NONE);

                // creo un ship aux para ser usado luego
                currentShip = new Ship(version, currentOrientation);

                if (version == 1) {
                    floatingShip = Figures.Fragata(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                } else if (version == 2) {
                    floatingShip = Figures.Destructor(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                }else if (version == 3){
                    floatingShip = Figures.Submarine(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                }
                else{
                    floatingShip = Figures.Portaaviones(Color.LIGHTBLUE, Color.BLACK); // nuevo clon visual
                }

                //añade el barco al anchorPane
                 rootPane.getChildren().add(floatingShip);


                //lo coloco para que siga el mouse
               floatingShip.setLayoutX(e.getSceneX() - 20);
               floatingShip.setLayoutY(e.getSceneY() - 40);
                //floatingShip.setLayoutX(e.getSceneX() - bounds.getWidth() / 2 - bounds.getMinX());
                //floatingShip.setLayoutY(e.getSceneY() - bounds.getHeight() / 2 - 3*bounds.getMinY());

                floatingShip.setMouseTransparent(true); // no intercepta eventos


                //código para que la figura siga el mouse
                rootPane.setOnMouseMoved(ev -> {

                    // la posición se ajusta al mouse
                    floatingShip.setLayoutX(ev.getSceneX() - 20);
                    floatingShip.setLayoutY(ev.getSceneY() - 40);




                     // CÓDIGO PARA LA AYUDA VISUAL AL JUGADOR
                    boolean isVertical = false; // o falso, según el estado actual

                    for (int i = 1; i < 101; i++) {
                        Node node = playerGrid.getChildren().get(i);

                        if (node instanceof StackPane stackPane) {
                            int index = i;

                            node.setOnMouseEntered(mouseEvent -> {
                                int row = index / 10;
                                int col = index % 10;

                                List<int[]> coords = playerBoard.getShipCoordinates(row, col, version, isVertical);

                                for (int[] coord : coords) {
                                    int r = coord[0];
                                    int c = coord[1];
                                    int targetIndex = r * 10 + c;
                                    if (targetIndex < playerGrid.getChildren().size()) {
                                        Node targetNode = playerGrid.getChildren().get(targetIndex);
                                        if (targetNode instanceof StackPane targetPane) {
                                            targetPane.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");
                                        }
                                    }
                                }
                            });

                            node.setOnMouseExited(mouseEvent -> {
                                int row = index / 10;
                                int col = index % 10;

                                List<int[]> coords = playerBoard.getShipCoordinates(row, col, version, isVertical);

                                for (int[] coord : coords) {
                                    int r = coord[0];
                                    int c = coord[1];
                                    int targetIndex = r * 10 + c;
                                    if (targetIndex < playerGrid.getChildren().size()) {
                                        Node targetNode = playerGrid.getChildren().get(targetIndex);
                                        if (targetNode instanceof StackPane targetPane) {
                                            targetPane.setStyle("-fx-border-color: black; -fx-background-color: white;");
                                        }
                                    }
                                }
                            });
                        }
                    }




                });



            });

            //lo añado al vbox donde van los barcos
            shipPalette.getChildren().add(shipVersion);


            }

     */


    /**
     *Dibujo el tablero, agrego regiones, manejo el ckick, dibujo el barco en el tablero
     *
     * METODO FUNCIONAL!!
     */
    /*
        private void drawBoard() {
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {


                    // agrega un stackpane para poder superponer los barcos
                    StackPane cell = new StackPane();
                    cell.setPrefSize(40, 40);
                    cell.setStyle("-fx-border-color: black; -fx-background-color: white;");


                    // Agrega una zona invisible para clics
                    Region clickArea = new Region();
                    clickArea.setPrefSize(40, 40);
                    clickArea.setStyle("-fx-background-color: transparent;");

                    int finalRow = j-1;
                    int finalCol = i-1;


                    //manejo la logica del modelo para ajustar donde colocar el barco y el controlador para hacerlo visualmente
                    clickArea.setOnMouseClicked(e -> {

                        if (currentShip != null && floatingShip != null) {
                            // 1. Eliminar barco flotante visual
                            rootPane.getChildren().remove(floatingShip);
                            rootPane.setOnMouseMoved(null);
                            //restaruo el mouse
                            //rootPane.setCursor(Cursor.DEFAULT);

                            //IMPORTANTE!!!!!!!!!!!!
                            // 2. Colocar visualmente el barco en la celda usando drawship y metodos del modelo!!!!!




                            // 3. Limpia estado actual y elimina del vbox el barco con la referencia del barco en la lista
                            currentShip = null;
                            floatingShip = null;
                            shipPalette.getChildren().remove(shipGraphics.get(currentShipId));



                            System.out.println("Barco colocado en " + finalRow + "," + finalCol);

                        } else {
                            System.out.println("No hay barco seleccionado, seleccione un barco ");
                        }

                    });

                    //añado la region al stackpane para que se pueda superponer la figura
                    cell.getChildren().add(clickArea);
                    playerGrid.add(cell, i, j);
                }

            }

        }
     */




    /**
     * NUEVO METODO
     */
    private void loadShipPalette(Group shipVersion,int version, int currentShipId){

        //escalamos y colocamos en la paleta de barcos
        shipVersion.setScaleX(0.78);
        shipVersion.setScaleY(0.78);
        shipPalette.getChildren().add(shipVersion);

        // Guardamos la posición original para poder "volver" si es necesario
        final double[] originalLayout = new double[2];
        final List<StackPane> previouslyHighlighted = new ArrayList<>();

        //PRESIONO UN SHIP
        shipVersion.setOnMousePressed(event -> {

            //ajustar la orientación visual
            dragging = true;
            currentShipVisual = shipVersion;

            // Guardar posición original
            originalLayout[0] = shipVersion.getLayoutX();
            originalLayout[1] = shipVersion.getLayoutY();

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

            //Guardamos la orientación
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
                    }
                }
            }

            event.consume();
        });


        //SUELTO UN SHIP
        shipVersion.setOnMouseReleased(event -> {


            // Por ahora, siempre vuelve a la paleta
            rootPane.setCursor(Cursor.DEFAULT);


            rootPane.getChildren().remove(shipVersion);


            shipPalette.getChildren().remove(shipVersion); // por si quedó por ahí
            shipPalette.getChildren().add(shipVersion);




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



    public StackPane getCellPane(GridPane grid, int row, int col) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                int nodeRow = GridPane.getRowIndex(node);
                int nodeCol = GridPane.getColumnIndex(node);
                if (nodeRow == row && nodeCol == col && node instanceof StackPane pane) {
                    return pane;
                }
            }
        }
        return null; // No encontrada
    }



    public void startPlay (Player player){
            this.player = player;
            System.out.println(player.getSunkenShips());
            System.out.println(player.getNickname());
        }


}
