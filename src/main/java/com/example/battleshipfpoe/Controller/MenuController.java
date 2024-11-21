package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import com.example.battleshipfpoe.Model.Boat.BoatVisuals;
import com.example.battleshipfpoe.View.GameStage;
import com.example.battleshipfpoe.View.PreparationStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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


    PreparationStage preparationStage;
    private boolean wasSnapped = false; // Nueva variable


    @FXML
    private Pane BoatPane;

    private boolean isPositionValid;

    private BoardHandler boardHandler;

    private final Map<Boat, int[]> boatPositionsMap = new HashMap<Boat, int[]>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBoard();
        BoatVisuals visuals = new BoatVisuals();

        BoatVisuals.Caravel caravel = visuals.new Caravel();
        BoatVisuals.Submarine submarine = visuals.new Submarine();
        BoatVisuals.Destroyer destroyer = visuals.new Destroyer();
        BoatVisuals.Aircraft aircraft = visuals.new Aircraft();

/*
        Boat boat1= new Boat(caravel.CaravelDrawer(),20, -100, 1, true);
        Boat boat2= new Boat(submarine.SubmarineDrawer(),20, 100, 3, true);
        Boat boat3 = new Boat(destroyer.DestroyerDrawer(), 20, 200, 2, true);
        Boat boat4 = new Boat(aircraft.AircraftDrawer(),-160, 0,4, true);
        addBoatToPane(boat1);
        addBoatToPane(boat2);
        addBoatToPane(boat3);
        addBoatToPane(boat4);*/


        // Separación entre los barcos
        int horizontalSpacing = 80; // Espaciado horizontal entre barcos
        int verticalSpacing = 100;  // Espaciado vertical entre filas

        // Crear Fragatas
        ArrayList<Boat> fragates = new ArrayList<>();

        int firstBoatXPosition = -40;
        int firstBoatYPosition = -140;

        // Creación de Fragatas con espaciado horizontal
        for (int i = 0; i < 4; i++) {
            fragates.add(new Boat(caravel.CaravelDrawer(),firstBoatXPosition +  horizontalSpacing, firstBoatYPosition, 1, true));
        }

        ArrayList<Boat> destroyers = new ArrayList<>();

        // Creación de Destroyers con espaciado horizontal
        for (int i = 0; i < 3; i++) {
            destroyers.add(new Boat(destroyer.DestroyerDrawer(),firstBoatXPosition +  (horizontalSpacing - 10), firstBoatYPosition + verticalSpacing + 10, 2, true));
        }

        ArrayList<Boat> submarines = new ArrayList<>();

        // Creación de Submarines con espaciado horizontal
        for (int i = 0; i < 2; i++) {
            submarines.add(new Boat(submarine.SubmarineDrawer(),firstBoatXPosition + (horizontalSpacing -3), firstBoatYPosition + ((2 * verticalSpacing) - 30), 3, true));
        }

        Boat aircraftBoat = new Boat(aircraft.AircraftDrawer(), firstBoatXPosition + horizontalSpacing - 220, firstBoatYPosition + 270, 4, true);


        // Añadir Fragatas al panel
        for (Boat fragate : fragates) {
            addBoatToPane(fragate);
        }

        // Añadir Destroyers al panel
        for (Boat destroyerBoat : destroyers) {
            addBoatToPane(destroyerBoat);
        }

        // Añadir Submarines al panel
        for (Boat submarineBoat : submarines) {
            addBoatToPane(submarineBoat);
        }

        addBoatToPane(aircraftBoat);

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

        // Ajustar el 'snap' de acuerdo a la orientación del barco
        if (boat.isRotated()) {
            col = (int) (newX / tileHeight); // Invertir el cálculo para la orientación vertical
            row = (int) (newY / tileWidth);  // Invertir el cálculo para la orientación vertical
        }


        if (ValidPlacement(boat, col, row)) {
            boat.setWasFirstMove(false);
            isPositionValid = true;
            isSnapped = true;



            // Si ya estaba snappeado y cambió de orientación, limpiar posición previa
            if (wasSnapped && boat.isRotated() != initialOrientation[0]) {
                boat.clearBoatPosition(boardHandler, initialOrientation[0]); // Borra usando orientación previa
            }


            switch(boat.getLength()){
                case 1: // Actualizar la posición después de la rotación
                    boat.setLayoutX((col * tileWidth) - 65);
                    boat.setLayoutY((row * tileHeight) - 150);
                    break;
                case 2:
                    if(boat.isHorizontal()){
                    boat.setLayoutX((col * tileWidth) - 40);
                    boat.setLayoutY((row * tileHeight) - 122);
                }else{
                    boat.setLayoutX((col * tileWidth) - 70);
                    boat.setLayoutY((row * tileHeight) - 93);
                }
                    break;
                case 3:
                    if(boat.isHorizontal()){
                        boat.setLayoutX((col * tileWidth) - 11);
                        boat.setLayoutY((row * tileHeight) - 150);
                    }else{
                        boat.setLayoutX((col * tileWidth) - 70);
                        boat.setLayoutY((row * tileHeight) - 93);
                    }
                    break;
                case 4:
                    if(boat.isHorizontal()){
                        boat.setLayoutX((col * tileWidth) - 195);
                        boat.setLayoutY((row * tileHeight) - 148);
                    }else{
                        boat.setLayoutX((col * tileWidth) - 288);
                        boat.setLayoutY((row * tileHeight) - 55);
                    }
                    break;


            }

            // Actualizar el estado de la posición
            boat.storePosition(row, col);
            boatPositionsMap.put(boat, new int[]{row, col});
            boat.updateBoatPosition(boardHandler);

            if (!BoardPane.getChildren().contains(boat)) {
                BoardPane.getChildren().add(boat);
            }

            boat.toFront();
            wasSnapped = true;
            return;
        }

        // Si la posición no es válida, revertir
        boat.setLayoutX(initialPosition[0]);
        boat.setLayoutY(initialPosition[1]);

        if (boat.isRotated() != initialOrientation[0]) {
            boat.rotate();
        }

        isPositionValid = false;
        isSnapped = false;

        if (!boat.isWasFirstMove()) {
            boat.updateBoatPosition(boardHandler);
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
            boat.clearBoatPosition(boardHandler, initialOrientation[0]);
            boardHandler.printBoard();
        });

        boat.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseOffset[0];
            double newY = event.getSceneY() - mouseOffset[1];

            // Si el barco está rotado, debes ajustar la posición
            if (boat.isRotated()) {
                // Calcular la nueva posición tomando en cuenta la rotación
                newX = event.getSceneX() - mouseOffset[0];
                newY = event.getSceneY() - mouseOffset[1];
            }

            boat.setLayoutX(newX);
            boat.setLayoutY(newY);
            boat.toFront();
            beingDragged = true;
        });

        boat.setOnKeyPressed(event -> {
            if (beingDragged && event.getCode() == KeyCode.R) {
                // Guardamos la posición inicial
                double oldX = boat.getLayoutX();
                double oldY = boat.getLayoutY();

                // Realizar la rotación
                boat.rotate();
                boat.setRotated(boat.isRotated());

                // Calcular el desplazamiento del barco debido a la rotación
                double offsetX = oldX - boat.getLayoutX();
                double offsetY = oldY - boat.getLayoutY();

                // Después de la rotación, ajustar la posición para que el barco quede bajo el clic
                boat.setLayoutX(oldX - offsetX); // Mantener la posición en X
                boat.setLayoutY(oldY - offsetY); // Mantener la posición en Y

                boat.toFront(); // Asegurarse de que el barco quede sobre otros elementos
            }
        });

        boat.setOnMouseReleased(event -> {
            // Realizar la validación al soltar el barco después de arrastrarlo
            snapToGrid(boat, event, initialPosition);
            boat.toFront();
            System.out.println("-------------------");
            System.out.println(isPositionValid);
            boardHandler.printBoard();
            beingDragged = false;
        });

        boat.setFocusTraversable(true); // Habilita el enfoque del barco para recibir eventos de teclado
    }


    private boolean ValidPlacement(Boat boat, int col, int row) {
        int boatSize = boat.getLength();

        if (!boat.isRotated()) {
            return isValidHorizontalPlacement(col, row, boatSize) && areCellsAvailableForHorizontal(col, row, boatSize);
        } else {
            return isValidVerticalPlacement(col, row, boatSize) && areCellsAvailableForVertical(col, row, boatSize);
        }
    }

    private boolean isValidHorizontalPlacement(int col, int row, int boatSize) {
        // Verificar que el barco no se desborde en la dirección horizontal
        return col >= 0 && col + boatSize <= boardHandler.getGridSize();
    }

    private boolean isValidVerticalPlacement(int col, int row, int boatSize) {
        // Verificar que el barco no se desborde en la dirección vertical
        return row >= 0 && row + boatSize <= boardHandler.getGridSize();
    }

    private boolean areCellsAvailableForHorizontal(int col, int row, int boatSize) {
        // Verificar que las celdas en la fila estén libres para el barco horizontal
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row, col + i) || boardHandler.getCell(row, col + i) == 1) {
                return false;
            }
        }
        return true;
    }

    private boolean areCellsAvailableForVertical(int col, int row, int boatSize) {
        // Verificar que las celdas en la columna estén libres para el barco vertical
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row + i, col) || boardHandler.getCell(row + i, col) == 1) {
                return false;
            }
        }
        return true;
    }


    public void handleNextButton(ActionEvent event) {
        try {
            // Obtener la instancia única de PreparationStage


            // Pasar la lista de barcos al controlador de GameController
            GameStage.getInstance();
            GameStage.getInstance().getGameController().newGameState();
            List<Boat> boatsList = new ArrayList<>(boatPositionsMap.keySet());
            GameStage.getInstance().getGameController().setBoatsList(boatsList);
            PreparationStage.deleteInstance();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}