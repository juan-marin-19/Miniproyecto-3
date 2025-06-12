package com.example.miniproyecto_3.controller;
import com.example.miniproyecto_3.model.*;

import com.example.miniproyecto_3.model.planeSerializableFiles.SeriazableFileHandler;
import com.example.miniproyecto_3.view.Figures;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class GameController {

    @FXML
    private GridPane playerGrid;

    @FXML
    private GridPane mainGrid;

    @FXML
    private AnchorPane playerAnchorPane;



    private Player player;
    private Machine machine;
    private Board playerBoard;
    //private Board machineBoard;
    private SeriazableFileHandler seriazableFileHandler;
    private boolean machineTurn = false;




    public GameController() {
    }

    public void initialize() {

        this.playerBoard = new Board(10, 10); // Solo si no fue seteado antes

       // this.machineBoard = new Board(10,10);

        this.seriazableFileHandler = new SeriazableFileHandler();

        this.machine = new Machine(0);

       // this.player = new Player("",0);
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

                if(shipThere)  stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: red;");

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
     * METODO donde se inicia la ventana de juego, se dibuja la pantalla luego de que board se halla puesto una vez se termino
     * de colocar en el placement controller.
     * */
    public void startGame() {

        playerBoard = (Board) seriazableFileHandler.deserialize("player_board.ser");
        machine.setBoard((Board) seriazableFileHandler.deserialize("machine_board.ser"));

        machine.getBoard().printCellGrid();

        // playerBoard.printCellGrid();

        drawGrids();
        drawShips(playerBoard,playerGrid);
        handlePlayerShot();


        //drawShips(machine.getBoard(),mainGrid);     // VISUALIZACIÓN TABLERO DEL OPONENTE O MAQUINA COLOCAR UN BOTÓN PARA LA OPCIÓN


        restoreEnemyGridVisuals();



    }

    /**
     * Coloco los barcos usando la primera celda de estos (PODRÍA USAR HERENCIA CÓDIGO SIMILAR MOUSE SET RELEASED)
     * */
    public void drawShips(Board board, GridPane gridPane) {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Ship ship = board.getCell(row, col).getShip();

                Node ref = getCellPane(gridPane, row+1, col+1);

                if (ship != null && !ship.getAux() && ref != null) {

                    ship.setAux(true);

                    Group shipGroup = Figures.createShip(ship.getSize(), Color.GRAY, Color.BLACK);

                    shipGroup.setScaleX(0.78);
                    shipGroup.setScaleY(0.78);


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
            if(node instanceof Button){
                int row = GridPane.getRowIndex(node)-1;
                int col = GridPane.getColumnIndex(node)-1;
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {


                    if(!machine.getBoard().getCell(row,col).isHit()){
                        machine.getBoard().getCell(row,col).hit();
                    }

                    //VUELO A ITERAR PARA CAMBIAR EL MAIN GRID ACORDE AL BARCO SUS VIDAS, Y SI HAY O NO UN BARCO.
                    for(Node n : mainGrid.getChildren()){
                        if(n instanceof Button){
                            int row_2 = GridPane.getRowIndex(n)-1;
                            int col_2 = GridPane.getColumnIndex(n)-1;
                            if(!machine.getBoard().getCell(row_2,col_2).isOccupied() && machine.getBoard().getCell(row_2,col_2).isHit() ){
                                n.setStyle("-fx-background-color: red;");
                            }
                            if(machine.getBoard().getCell(row_2,col_2).isOccupied() && machine.getBoard().getCell(row_2,col_2).isHit()){
                                if(machine.getBoard().getCell(row_2,col_2).getShip().getLifes() >0 ){
                                    n.setStyle("-fx-background-color: lightgreen;");
                                }
                                if (machine.getBoard().getCell(row_2,col_2).getShip().getLifes() ==0) {
                                    n.setStyle("-fx-background-color: green;");
                                }
                            }
                        }

                    }

                    node.setDisable(true);




                });




            }
        }
    }


    /**
     * Coloco el player ya sea desde el controlador placement o si ya había una partida, coloco el player con los datos
     * guardados (nombre y barcos hundidos)
     * */
    public void setPlayer(Player player) {
        this.player = player;
        System.out.println(player.getSunkenShips());
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


    public void restoreEnemyGridVisuals() {
        for (Node node : mainGrid.getChildren()) {
            if (node instanceof Button) {
                int row = GridPane.getRowIndex(node) - 1;
                int col = GridPane.getColumnIndex(node) - 1;

                Cell cell = machine.getBoard().getCell(row, col);

                if (cell.isHit()) {
                    if (!cell.isOccupied()) {
                        node.setStyle("-fx-background-color: red;");
                    } else {
                        Ship ship = cell.getShip();
                        if (ship.getLifes() > 0) {
                            node.setStyle("-fx-background-color: lightgreen;");
                        } else {
                            node.setStyle("-fx-background-color: green;");
                        }
                    }

                    node.setDisable(true);
                }
            }
        }
    }

    public void saveGame() {
        seriazableFileHandler.serialize("player_board.ser", playerBoard);
        seriazableFileHandler.serialize("machine_board.ser", machine.getBoard());
        System.out.println("Juego guardado al cerrar la ventana.");
    }


}



