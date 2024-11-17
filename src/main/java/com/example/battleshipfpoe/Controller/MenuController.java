package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.View.Boat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane BoardPane;

    private List<int[]> boatPositions = new ArrayList<>();


    @FXML
    private Pane BoatPane;

    private BoardHandler boardHandler;

    private Map<Rectangle, int[]> boatPositionsMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBoard();

        // Agregar rectángulos como barcos temporales
        addBoatToPane(new Rectangle(), 20, 20);
        addBoatToPane(new Rectangle(), 20, 100);
    }


    private void initializeBoard() {
        double planeWidth = 600;
        double planeHeight = 600;
        int gridSize = 10;


        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, BoardPane);

        boardHandler.updateGrid();
    }

    private void addBoatToPane(Rectangle boat, double x, double y) {
        boat.setLayoutX(x);
        boat.setLayoutY(y);
        boat.setFill(Color.LIGHTGREEN);
        boat.setStroke(Color.BLACK);
        boat.setWidth(50);
        boat.setHeight(50);

        setupDragAndDrop(boat);

        BoatPane.getChildren().add(boat);
    }

    private void setupDragAndDrop(Rectangle boat) {
        final double[] initialPosition = new double[2];
        final double[] mouseOffset = new double[2];

        boat.setOnMousePressed(event -> {
            initialPosition[0] = boat.getLayoutX();
            initialPosition[1] = boat.getLayoutY();

            mouseOffset[0] = event.getSceneX() - boat.getLayoutX();
            mouseOffset[1] = event.getSceneY() - boat.getLayoutY();
            boat.toFront();
        });

        boat.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseOffset[0];
            double newY = event.getSceneY() - mouseOffset[1];
            boat.setLayoutX(newX);
            boat.setLayoutY(newY);
            boat.toFront();
        });

        boat.setOnMouseReleased(event -> {
            snapToGrid(boat, event, initialPosition);
            boat.toFront();
        });
    }


    private void snapToGrid(Rectangle boat, MouseEvent event, double[] initialPosition) {
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double newX = event.getSceneX() - boardX;
        double newY = event.getSceneY() - boardY;

        double tileWidth = boardHandler.getTilesAcross();
        double tileHeight = boardHandler.getTilesDown();
        int col = (int) (newX / tileWidth);
        int row = (int) (newY / tileHeight);

        if (row >= 0 && row < boardHandler.getGridSize() && col >= 0 && col < boardHandler.getGridSize()) {
            boolean isPositionChanged = false;

            if (boatPositionsMap.containsKey(boat)) {
                boatPositionsMap.remove(boat);
            }

            for (var node : BoardPane.getChildren()) {
                if (node instanceof Pane) {
                    Pane cell = (Pane) node;
                    int[] cellCoords = (int[]) cell.getUserData();

                    if (cellCoords[0] == row && cellCoords[1] == col) {
                        if (!BoardPane.getChildren().contains(boat)) {
                            BoardPane.getChildren().add(boat);
                        }

                        boat.setLayoutX(col * tileWidth);
                        boat.setLayoutY(row * tileHeight);
                        boat.toFront(); // Asegura que esté al frente

                        boatPositionsMap.put(boat, new int[]{row, col});

                        if (!boatPositions.contains(new int[]{row, col})) {
                            boatPositions.add(new int[]{row, col});
                        }

                        boardHandler.placeShip(row, col);

                        System.out.println("Barco colocado en la celda: Fila " + row + ", Columna " + col);
                        isPositionChanged = true;
                        return;
                    }
                }
            }

            if (!isPositionChanged) {
                boat.setLayoutX(initialPosition[0]);
                boat.setLayoutY(initialPosition[1]);
            }
        } else {
            boat.setLayoutX(initialPosition[0]);
            boat.setLayoutY(initialPosition[1]);
        }
    }
    public void handleNextButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();

            // Crear una lista que solo contenga las últimas posiciones
            List<int[]> finalBoatPositions = new ArrayList<>();
            for (Map.Entry<Rectangle, int[]> entry : boatPositionsMap.entrySet()) {
                int[] finalPosition = entry.getValue();
                finalBoatPositions.add(finalPosition);
            }

            // Pasar las posiciones finales de los barcos al GameController
            gameController.setBoatPositions(finalBoatPositions);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}