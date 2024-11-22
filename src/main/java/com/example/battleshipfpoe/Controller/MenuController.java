package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import com.example.battleshipfpoe.Model.Boat.Boat;
import com.example.battleshipfpoe.Model.Boat.BoatVisuals;
import com.example.battleshipfpoe.Model.SaveSystem.PlainTextGameStateManager;
import com.example.battleshipfpoe.Model.SaveSystem.PlainTextSaveHandler;
import com.example.battleshipfpoe.Model.SaveSystem.SaveSystem;
import com.example.battleshipfpoe.View.GameStage;
import com.example.battleshipfpoe.View.PreparationStage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class responsible for managing the user interface of a menu in a game.
 * It handles the initialization of the game board, boat placement, drag and drop functionality, and validates boat placements.
 * It also saves the game state using a plain text system.
 */
public class MenuController implements Initializable {

    @FXML
    private AnchorPane BoardPane;

    @FXML
    private TextField nickTxtField;

    private boolean isSnapped;
    private boolean beingDragged;

    final boolean[] initialOrientation = new boolean[1];

    private boolean isWarningVisible = false;

    private int unplacedBoats = 10;
    boolean isThereUnplacedBoats = true;

    private PlainTextGameStateManager plainTextGameStateManager;
    private final String TextSaveFilePath = "TextGameState.txt";

    private PreparationStage preparationStage;
    private boolean wasSnapped = false;

    @FXML
    private Pane BoatPane;

    @FXML
    private Label warningLabel;

    private boolean isPositionValid;

    private BoardHandler boardHandler;

    private final Map<Boat, int[]> boatPositionsMap = new HashMap<Boat, int[]>();

    /**
     * Initializes the game board, sets up boat visuals, and adds boats to the pane.
     * It also sets up the save system for the game state.
     *
     * @param url            the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBoard();
        BoatVisuals visuals = new BoatVisuals();
        warningLabel.setOpacity(0);

        BoatVisuals.Caravel caravel = visuals.new Caravel();
        BoatVisuals.Submarine submarine = visuals.new Submarine();
        BoatVisuals.Destroyer destroyer = visuals.new Destroyer();
        BoatVisuals.Aircraft aircraft = visuals.new Aircraft();

        // Set spacing between boats
        int horizontalSpacing = 80;
        int verticalSpacing = 100;

        // Create boat arrays
        ArrayList<Boat> fragates = new ArrayList<>();
        int firstBoatXPosition = -40;
        int firstBoatYPosition = -140;

        // Create fragates
        for (int i = 0; i < 4; i++) {
            fragates.add(new Boat(caravel.CaravelDrawer(), firstBoatXPosition + horizontalSpacing, firstBoatYPosition, 1, true, 1));
        }

        ArrayList<Boat> destroyers = new ArrayList<>();
        // Create destroyers
        for (int i = 0; i < 3; i++) {
            destroyers.add(new Boat(destroyer.DestroyerDrawer(), firstBoatXPosition + (horizontalSpacing - 10), firstBoatYPosition + verticalSpacing + 10, 2, true, 2));
        }

        ArrayList<Boat> submarines = new ArrayList<>();
        // Create submarines
        for (int i = 0; i < 2; i++) {
            submarines.add(new Boat(submarine.SubmarineDrawer(), firstBoatXPosition + (horizontalSpacing - 3), firstBoatYPosition + ((2 * verticalSpacing) - 30), 3, true, 3));
        }

        Boat aircraftBoat = new Boat(aircraft.AircraftDrawer(), firstBoatXPosition + horizontalSpacing - 220, firstBoatYPosition + 270, 4, true, 4);

        // Add boats to the pane
        for (Boat fragate : fragates) {
            addBoatToPane(fragate);
            fragate.setWasFirstMove(true);
        }

        for (Boat destroyerBoat : destroyers) {
            addBoatToPane(destroyerBoat);
            destroyerBoat.setWasFirstMove(true);
        }

        for (Boat submarineBoat : submarines) {
            addBoatToPane(submarineBoat);
            submarineBoat.setWasFirstMove(true);
        }

        addBoatToPane(aircraftBoat);

        // Setup save system
        PlainTextSaveHandler plainTextHandler = new PlainTextSaveHandler();
        SaveSystem<String> textSaveSystem = new SaveSystem<>(plainTextHandler);
        plainTextGameStateManager = new PlainTextGameStateManager(textSaveSystem);
    }

    /**
     * Initializes the board with specific dimensions and grid size.
     */
    private void initializeBoard() {
        double planeWidth = 600;
        double planeHeight = 600;
        int gridSize = 10;

        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, BoardPane);
        boardHandler.updateGrid(false);
    }

    /**
     * Adds a boat to the boat pane and sets up its drag-and-drop functionality.
     *
     * @param boat the boat to be added to the pane
     */
    private void addBoatToPane(Boat boat) {
        setupDragAndDrop(boat);
        BoatPane.getChildren().add(boat);
        boat.setBoardHandler(boardHandler);
        boat.requestFocus();
    }

    /**
     * Snaps a boat to the grid when it is dragged.
     *
     * @param boat            the boat being snapped to the grid
     * @param event           the mouse event triggered during dragging
     * @param initialPosition the initial position of the boat before dragging
     */
    private void snapToGrid(Boat boat, MouseEvent event, double[] initialPosition) {
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double newX = event.getSceneX() - boardX;
        double newY = event.getSceneY() - boardY;

        double tileWidth = boardHandler.getTilesAcross();
        double tileHeight = boardHandler.getTilesDown();
        int col = (int) (newX / tileWidth);
        int row = (int) (newY / tileHeight);
        int horizontal;

        // Adjust snap if the boat is rotated
        if (boat.isRotated()) {
            col = (int) (newX / tileHeight); // Swap calculations for vertical orientation
            row = (int) (newY / tileWidth);  // Swap calculations for vertical orientation
        }

        if (ValidPlacement(boat, col, row)) {
            isPositionValid = true;
            isSnapped = true;
            if (boat.isWasFirstMove()) {
                unplacedBoats--;
            }

            boat.setWasFirstMove(false);

            // If boat was snapped and orientation changed, clear previous position
            if (wasSnapped && boat.isRotated() != initialOrientation[0]) {
                boat.clearBoatPosition(boardHandler, initialOrientation[0]);
            }

            // Set boat position according to its length and orientation
            switch (boat.getLength()) {
                case 1:
                    boat.setLayoutX((col * tileWidth) - 65);
                    boat.setLayoutY((row * tileHeight) - 150);
                    break;
                case 2:
                    if (boat.isHorizontal()) {
                        boat.setLayoutX((col * tileWidth) - 40);
                        boat.setLayoutY((row * tileHeight) - 122);
                    } else {
                        boat.setLayoutX((col * tileWidth) - 70);
                        boat.setLayoutY((row * tileHeight) - 93);
                    }
                    break;
                case 3:
                    if (boat.isHorizontal()) {
                        boat.setLayoutX((col * tileWidth) - 11);
                        boat.setLayoutY((row * tileHeight) - 150);
                    } else {
                        boat.setLayoutX((col * tileWidth) - 70);
                        boat.setLayoutY((row * tileHeight) - 93);
                    }
                    break;
                case 4:
                    if (boat.isHorizontal()) {
                        boat.setLayoutX((col * tileWidth) - 195);
                        boat.setLayoutY((row * tileHeight) - 148);
                    } else {
                        boat.setLayoutX((col * tileWidth) - 288);
                        boat.setLayoutY((row * tileHeight) - 55);
                    }
                    break;
            }

            // Store the boat's position
            boat.storePosition(row, col);
            horizontal = boat.isHorizontal() ? 1 : 0;
            boatPositionsMap.put(boat, new int[]{row, col, horizontal, boat.getType()});
            boat.updateBoatPosition(boardHandler);

            if (!BoardPane.getChildren().contains(boat)) {
                BoardPane.getChildren().add(boat);
            }

            boat.toFront();
            wasSnapped = true;
            return;
        }

        // If the position is invalid, revert to the initial position
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

    /**
     * Sets up the drag-and-drop functionality for a boat.
     *
     * @param boat the boat to be set up for drag-and-drop functionality
     */
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
            boat.requestFocus();
            boat.clearBoatPosition(boardHandler, initialOrientation[0]);
            boardHandler.printBoard();
        });

        boat.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseOffset[0];
            double newY = event.getSceneY() - mouseOffset[1];

            if (boat.isRotated()) {
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
                double oldX = boat.getLayoutX();
                double oldY = boat.getLayoutY();

                boat.rotate();
                boat.setRotated(boat.isRotated());

                double offsetX = oldX - boat.getLayoutX();
                double offsetY = oldY - boat.getLayoutY();

                boat.setLayoutX(oldX - offsetX);
                boat.setLayoutY(oldY - offsetY);

                boat.toFront();
            }
        });

        boat.setOnMouseReleased(event -> {
            snapToGrid(boat, event, initialPosition);
            boat.toFront();
            beingDragged = false;
        });

        boat.setFocusTraversable(true);
    }

    /**
     * Validates whether a boat's proposed placement is within bounds and does not overlap with other boats.
     *
     * @param boat the boat being validated
     * @param col  the column index of the boat's position
     * @param row  the row index of the boat's position
     * @return true if the placement is valid, false otherwise
     */
    private boolean ValidPlacement(Boat boat, int col, int row) {
        int boatSize = boat.getLength();

        if (!boat.isRotated()) {
            return isValidHorizontalPlacement(col, row, boatSize) && areCellsAvailableForHorizontal(col, row, boatSize);
        } else {
            return isValidVerticalPlacement(col, row, boatSize) && areCellsAvailableForVertical(col, row, boatSize);
        }
    }


    /**
     * Validates if the horizontal placement of a boat is within the grid's boundaries and does not exceed the grid size.
     *
     * @param col      the column index where the boat will be placed
     * @param row      the row index where the boat will be placed
     * @param boatSize the size (length) of the boat being placed
     * @return true if the boat's horizontal placement is valid, false otherwise
     */
    private boolean isValidHorizontalPlacement(int col, int row, int boatSize) {
        return col >= 0 && col + boatSize <= boardHandler.getGridSize();
    }

    /**
     * Validates if the vertical placement of a boat is within the grid's boundaries and does not exceed the grid size.
     *
     * @param col      the column index where the boat will be placed
     * @param row      the row index where the boat will be placed
     * @param boatSize the size (length) of the boat being placed
     * @return true if the boat's vertical placement is valid, false otherwise
     */
    private boolean isValidVerticalPlacement(int col, int row, int boatSize) {
        return row >= 0 && row + boatSize <= boardHandler.getGridSize();
    }

    /**
     * Checks if the cells required for a boat's horizontal placement are available (i.e., they are within bounds and not already occupied).
     *
     * @param col      the starting column index where the boat will be placed
     * @param row      the row index where the boat will be placed
     * @param boatSize the size (length) of the boat being placed
     * @return true if all required cells for the boat's horizontal placement are available, false otherwise
     */
    private boolean areCellsAvailableForHorizontal(int col, int row, int boatSize) {
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row, col + i) || boardHandler.getCell(row, col + i) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the cells required for a boat's vertical placement are available (i.e., they are within bounds and not already occupied).
     *
     * @param col      the column index where the boat will be placed
     * @param row      the starting row index where the boat will be placed
     * @param boatSize the size (length) of the boat being placed
     * @return true if all required cells for the boat's vertical placement are available, false otherwise
     */
    private boolean areCellsAvailableForVertical(int col, int row, int boatSize) {
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row + i, col) || boardHandler.getCell(row + i, col) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles the event when the "Next" button is clicked. Validates whether the user has completed all required steps (e.g., providing a nickname and placing all boats).
     * If valid, the game state is saved and the game stage is transitioned to the next screen.
     * If invalid, a warning label will blink to notify the user.
     *
     * @param event the action event triggered by clicking the "Next" button
     */
    public void handleNextButton(ActionEvent event) {
        boolean isEmptyNick = nickTxtField.getText().isEmpty();

        if (unplacedBoats == 0) {
            isThereUnplacedBoats = false;
        } else {
            isThereUnplacedBoats = true;
        }

        if (isEmptyNick || isThereUnplacedBoats) {
            isWarningVisible = true;
        } else {
            isWarningVisible = false;
        }

        if (isWarningVisible) {
            // Animate warning label with blinking effect
            FadeTransition blink = new FadeTransition(Duration.millis(300), warningLabel);
            blink.setFromValue(0);
            blink.setToValue(1);
            blink.setCycleCount(8);
            blink.setAutoReverse(true);
            blink.play();
        } else {
            try {
                // Save the game state with the player's nickname
                plainTextGameStateManager.saveGame(nickTxtField.getText(), TextSaveFilePath);

                // Transition to the game stage
                GameStage.getInstance();
                GameStage.getInstance().getGameController().setPlayerText(nickTxtField.getText());
                GameStage.getInstance().getGameController().newGameState();

                // Pass the boat list to the game controller
                List<Boat> boatsList = new ArrayList<>(boatPositionsMap.keySet());
                GameStage.getInstance().getGameController().setBoatsList(boatsList);

                // Delete the current preparation stage instance
                PreparationStage.deleteInstance();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the event when the "Close Preparation Window" button is clicked.
     * Deletes the current instance of the preparation stage.
     *
     * @param event the action event triggered by closing the preparation window
     */
    public void handleClosePreparationWindow(ActionEvent event) {
        PreparationStage.deleteInstance();
    }
}