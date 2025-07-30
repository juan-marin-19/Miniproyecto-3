package com.example.miniproyecto_3.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;


/**
 * Adapter class
 * */
public abstract class AdapterControlller {

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
}
