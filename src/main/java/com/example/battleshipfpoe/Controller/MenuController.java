package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.View.Boat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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

    private void setupDragAndDrop(Boat boat) {
        boat.getBoat().setOnMouseReleased(event -> {
            // Verificar si el barco está sobre el tablero
            if (isOverBoardPane(event)) {
                // Remover del panel actual y agregar al tablero
                BoatPane.getChildren().remove(boat.getBoat());
                BoardPane.getChildren().add(boat.getBoat());

                // Ajustar las coordenadas al tablero
                boat.getBoat().setLayoutX(event.getSceneX() - BoardPane.getLayoutX());
                boat.getBoat().setLayoutY(event.getSceneY() - BoardPane.getLayoutY());
            }
        });
    }

    private boolean isOverBoardPane(MouseEvent event) {
        // Calcular los límites del BoardPane
        double boardX = BoardPane.getLayoutX();
        double boardY = BoardPane.getLayoutY();
        double boardWidth = BoardPane.getWidth();
        double boardHeight = BoardPane.getHeight();

        // Verificar si el mouse está dentro de los límites
        return event.getSceneX() > boardX && event.getSceneX() < boardX + boardWidth &&
                event.getSceneY() > boardY && event.getSceneY() < boardY + boardHeight;
    }
}