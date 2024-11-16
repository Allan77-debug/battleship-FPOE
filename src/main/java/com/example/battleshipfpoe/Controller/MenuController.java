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

        // Configurar eventos para arrastrar y soltar entre paneles
        setupDragAndDrop(boat);

        // Agregar el barco al panel de preparación
        BoatPane.getChildren().add(boat.getBoat());
    }

    private void setupDragAndDrop(Boat boat) {
        boat.getBoat().setOnMouseReleased(event -> {
            // Verificar si el barco está sobre el tablero
            if (isOverBoardPane(event)) {
                // Transferir el barco al tablero
                transferBoatToBoard(boat, event);
            }
        });
    }

    private boolean isOverBoardPane(MouseEvent event) {
        // Obtener las coordenadas absolutas del tablero
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double boardWidth = BoardPane.getWidth();
        double boardHeight = BoardPane.getHeight();

        // Verificar si el mouse está dentro de los límites del tablero
        return event.getSceneX() > boardX && event.getSceneX() < boardX + boardWidth &&
                event.getSceneY() > boardY && event.getSceneY() < boardY + boardHeight;
    }

    private void transferBoatToBoard(Boat boat, MouseEvent event) {
        // Remover del contenedor inicial
        BoatPane.getChildren().remove(boat.getBoat());

        // Agregar al tablero
        BoardPane.getChildren().add(boat.getBoat());

        // Ajustar las coordenadas del barco al tablero
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double newX = event.getSceneX() - boardX;
        double newY = event.getSceneY() - boardY;

        // Asegurarse de que las coordenadas estén dentro de los límites del tablero
        if (newX >= 0 && newX <= BoardPane.getWidth() - boat.getBoat().getLayoutBounds().getWidth()) {
            boat.getBoat().setLayoutX(newX);
        }
        if (newY >= 0 && newY <= BoardPane.getHeight() - boat.getBoat().getLayoutBounds().getHeight()) {
            boat.getBoat().setLayoutY(newY);
        }
    }
}