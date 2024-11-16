package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.View.Boat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane BoardPane;

    @FXML
    private Pane BoatPane;

    private BoardHandler boardHandler;

    private Ship selectedShip; // Barco actualmente seleccionado

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar el tablero
        initializeBoard();

        // Agregar barcos al BoatPane
        addBoatToPane(new Boat(), 20, 20); // Primer barco
        addBoatToPane(new Boat(), 20, 100); // Segundo barco
    }

    private void initializeBoard() {
        double planeWidth = 600;  // Ajusta según sea necesario
        double planeHeight = 600;
        int gridSize = 10;        // Tamaño del tablero (10x10)

        // Inicializar el tablero
        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, BoardPane);

        // Dibujar el tablero
        boardHandler.updateGrid();
        boardHandler.printBoard();
    }

    private void addBoatToPane(Boat boat, double x, double y) {
        boat.getBoat().setLayoutX(x);
        boat.getBoat().setLayoutY(y);

        // Agregar el barco al panel de preparación
        BoatPane.getChildren().add(boat.getBoat());
    }
}

