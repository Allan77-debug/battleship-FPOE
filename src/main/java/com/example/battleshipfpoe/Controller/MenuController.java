package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane BoardPane;
    boolean isSnapped;
    boolean beingDragged;
    final boolean[] initialOrientation = new boolean[1];



    @FXML
    private Pane BoatPane;

    private boolean isPositionValid;

    private BoardHandler boardHandler;

    private final Map<Boat, int[]> boatPositionsMap = new HashMap<Boat, int[]>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBoard();

        Boat boat2 = new Boat(20, 100, 2, true);
        Boat boat3 = new Boat(20, 200, 3, true);
        addBoatToPane(boat2);
        addBoatToPane(boat3);
    }

    private void initializeBoard() {
        double planeWidth = 600;
        double planeHeight = 600;
        int gridSize = 10;


        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, BoardPane);

        boardHandler.updateGrid(false);
    }

    private void addBoatToPane(Boat boat) {
        setupDragAndDrop(boat);
        BoatPane.getChildren().add(boat);
        boat.setBoardHandler(boardHandler);
        boat.requestFocus();
    }

    private void snapToGrid(Boat boat, MouseEvent event, double[] initialPosition) {
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double newX = event.getSceneX() - boardX;
        double newY = event.getSceneY() - boardY;

        double tileWidth = boardHandler.getTilesAcross();
        double tileHeight = boardHandler.getTilesDown();
        int col = (int) (newX / tileWidth);
        int row = (int) (newY / tileHeight);

        if (ValidPlacement(boat, col, row)) {
            boat.setWasFirstMove(false);
            isPositionValid = true;
            isSnapped = true;
            boat.clearBoatPosition(boardHandler);

            // Snap the boat to the new grid position
            boat.setLayoutX(col * tileWidth);
            boat.setLayoutY(row * tileHeight);

            // Update the board matrix with the new position of the boat
            boat.storePosition(row, col);
            boatPositionsMap.put(boat, new int[]{row, col});
            boat.updateBoatPosition(boardHandler);

            // Add the boat to the pane if it's not already there
            if (!BoardPane.getChildren().contains(boat)) {
                BoardPane.getChildren().add(boat);
            }

            boat.toFront();
            return;
        }

        // Si la colocación es inválida, revertir al estado original y limpiar la posición en la matriz
        if (!ValidPlacement(boat, col, row)) {
            // Restaurar posición y orientación inicial
            boat.setLayoutX(initialPosition[0]);
            boat.setLayoutY(initialPosition[1]);

            if (boat.isRotated() != initialOrientation[0]) {
                boat.rotate(); // Cambia de orientación si fue rotado durante el movimiento
            }

            isPositionValid = false;
            isSnapped = false;

            if (!boat.isWasFirstMove()) {
                boat.updateBoatPosition(boardHandler);
            }
            return;
        }


        // Si el barco regresa a su posición inicial y nunca se colocó, marcar como no movido
        if (boat.getLayoutX() == boardX && boat.getLayoutY() ==boardY) {
            boat.setWasFirstMove(true);
        }
    }

    private void setupDragAndDrop(Boat boat) {
        final double[] initialPosition = new double[2];
        final double[] mouseOffset = new double[2];


        boat.setOnMousePressed(event -> {

            initialPosition[0] = boat.getLayoutX();
            initialPosition[1] = boat.getLayoutY();
            initialOrientation[0] = boat.isRotated();


            mouseOffset[0] = event.getSceneX() - boat.getLayoutX();
            mouseOffset[1] = event.getSceneY() - boat.getLayoutY();
            boat.toFront();
            boat.requestFocus(); // Asegura que el barco recibe el enfoque
            boat.clearBoatPosition(boardHandler);
        });

        boat.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseOffset[0];
            double newY = event.getSceneY() - mouseOffset[1];
            boat.setLayoutX(newX);
            boat.setLayoutY(newY);
            boat.toFront();
            beingDragged = true;
        });

        // En la rotación, ajusta la posición después de la rotación y luego realiza el "snap to grid"
        boat.setOnKeyPressed(event -> {
            if (beingDragged && event.getCode() == KeyCode.R) {
                boat.rotate();
                boat.setRotated(boat.isRotated());
                boat.toFront();
            }
        });

        boat.setOnMouseReleased(event -> {
            // Realizar la validación al soltar el barco después de arrastrarlo


            snapToGrid(boat, event, initialPosition);

            boat.toFront();
            beingDragged = false;
        });

        boat.setFocusTraversable(true); // Habilita el enfoque del barco para recibir eventos de teclado
    }


    private boolean ValidPlacement(Boat boat, int col, int row) {
        int boatSize = boat.getChildren().size();
        if (!boat.isRotated()) {
            // Horizontal ship check (ensure col + i doesn't go out of bounds)
            if (col + boatSize - 1 >= boardHandler.getGridSize()) {
                return false;
            }

            // Check if the cells for the ship's horizontal placement are available
            for (int i = 0; i < boatSize; i++) {
                // Use the boundary-checking method
                if (!boardHandler.isWithinBounds(row, col + i) || boardHandler.getCell(row, col + i) == 1) {
                    return false;
                }
            }

        } else {
            // Vertical ship check (ensure row + i doesn't go out of bounds)
            if (row + boatSize - 1 >= boardHandler.getGridSize()) {
                return false;
            }

            // Check if the cells for the ship's vertical placement are available
            for (int i = 0; i < boatSize; i++) {
                // Use the boundary-checking method
                if (!boardHandler.isWithinBounds(row + i, col) || boardHandler.getCell(row + i, col) == 1) {
                    return false;
                }
            }

        }
        return true;
    }


    public void handleNextButton(ActionEvent event) {
        System.out.println("-------END OF SETTING BOATS-------");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            gameController.newGameState();
            // Pass the list of Boat objects to the GameController
            List<Boat> boatsList = new ArrayList<>(boatPositionsMap.keySet());

            // Pass the list of boats to the GameController
            gameController.setBoatsList(boatsList);

            gameController.saveGameState();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}