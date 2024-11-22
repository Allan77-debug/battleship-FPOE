package com.example.battleshipfpoe.Model.Boat;

import com.example.battleshipfpoe.Model.Board.BoardHandler;
import javafx.scene.Group;


/**
 * Represents a boat in the Battleship game.
 * Handles the visual representation, movement, rotation, and position updates of the boat.
 */
public class Boat extends Group implements BoatInterface {

    private static final int SQUARE_SIZE = 60; // Size of one square
    private int length; // Length of the boat
    private boolean isHorizontal; // Orientation of the boat (horizontal or vertical)
    private double startX, startY; // Initial position of the boat
    private int currentRow = -1; // Current row of the boat on the board
    private int currentCol = -1; // Current column of the boat on the board
    private BoardHandler boardHandler; // Reference to the game board
    private boolean rotated = false; // Tracks the rotation state of the boat
    private int type; // Type identifier for the boat
    private boolean wasFirstMove = true; // Indicates if the boat has been moved for the first time
    private Group boatDesign; // Visual representation of the boat


    /**
     * Constructor for creating a new Boat instance.
     *
     * @param boatDesign   The visual design of the boat.
     * @param startX       Initial X position.
     * @param startY       Initial Y position.
     * @param length       Length of the boat (number of squares it occupies).
     * @param isHorizontal Initial orientation (true for horizontal, false for vertical).
     * @param type         The type identifier of the boat.
     */
    public Boat(Group boatDesign, double startX, double startY, int length, boolean isHorizontal, int type) {
        this.startX = startX;
        this.startY = startY;
        this.length = length;
        this.isHorizontal = true;
        this.boatDesign = boatDesign;
        this.type = type;
        // Call placeBoat to initialize the boat
        placeBoat(boatDesign, startX, startY, length, isHorizontal);

        setupInteractions();
    }


    /**
     * Places the boat visually at a specified position with a given orientation.
     *
     * @param boatDesign   The visual design of the boat.
     * @param startX       Starting X position.
     * @param startY       Starting Y position.
     * @param length       Length of the boat.
     * @param isHorizontal Orientation of the boat.
     */
    @Override
    public void placeBoat(Group boatDesign, double startX, double startY, int length, boolean isHorizontal) {


        if(isHorizontal){
            boatDesign.setRotate(-90);
        }

        // Añadir al Group del barco
        getChildren().clear(); // Asegurarse de limpiar cualquier contenido previo
        getChildren().add(boatDesign);
        

            // Set the initial position of the boat
            setLayoutX(startX);
            setLayoutY(startY);
            toFront();
            boatDesign.toFront();// Ensure the boat is at the front of the scene
        }

    /**
     * Gets the type of the boat based on its length.
     *
     * @return The type of the boat.
     */
   public int getType(){
        switch(getLength()){
            case 1: type = 1;
            break;
            case 2: type = 2;
            break;
            case 3: type = 3;
            break;
            case 4: type = 4;
            break;
        }
        return type;
   }

    /**
     * Stores the current position of the boat on the board.
     *
     * @param row The row of the boat's position.
     * @param col The column of the boat's position.
     */
    public void storePosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
        System.out.println("Snapped at row: " + currentRow + ", col: " + currentCol);
    }

    /**
     * Clears the boat's current position from the board.
     *
     * @param boardHandler        The game board handler.
     * @param previousOrientation The previous orientation of the boat (true for vertical, false for horizontal).
     */
    public void clearBoatPosition(BoardHandler boardHandler, boolean previousOrientation) {
        if (currentRow == -1 || currentCol == -1) {
            return;
        }

        // Limpiar según la orientación previa
        if (!previousOrientation) { // Horizontal
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow, currentCol + i, 0);
            }
        } else { // Vertical
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow + i, currentCol, 0);
            }
        }
    }

    /**
     * Updates the boat's position on the board based on its current location.
     *
     * @param boardHandler The game board handler.
     */
    public void updateBoatPosition(BoardHandler boardHandler) {
        for (int i = 0; i < length; i++) {
            if (isHorizontal) {
                boardHandler.setCell(currentRow, currentCol + i, 1);
            } else {
                boardHandler.setCell(currentRow + i, currentCol, 1);
            }
        }
    }

    public Group getBoatGroup() {return boatDesign;}
    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Sets the horizontal orientation of the boat.
     *
     * @param horizontal True for horizontal, false for vertical.
     */
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    /**
     * Gets the current position of the boat on the board.
     *
     * @return An array with the row and column of the boat's position.
     */
    public int[] getPosition() {
        return new int[]{currentRow, currentCol};
    }

    /**
     * Sets the board handler for the boat.
     *
     * @param boardHandler The game board handler.
     */
    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    /**
     * Configures interactions for dragging and selecting the boat.
     */
    private void setupInteractions() {
        this.setOnMousePressed(event -> {
            this.requestFocus(); // Obtener el foco cuando se haga clic en el barco
            this.setUserData(new double[]{
                    event.getSceneX() - this.getLayoutX(),
                    event.getSceneY() - this.getLayoutY()
            });
            this.requestFocus();
        });

        this.setOnMouseDragged(event -> {
            double[] offsets = (double[]) this.getUserData();
            double newX = event.getSceneX() - offsets[0];
            double newY = event.getSceneY() - offsets[1];

            this.setLayoutX(newX);
            this.setLayoutY(newY);

        });

    }

    /**
     * Rotates the boat and adjusts its visual representation and position.
     */
    public void rotate() {
        rotated = !rotated;
        isHorizontal = !isHorizontal;

        // Al rotar, se ajusta la posición del barco para que quede debajo del mouse
        double currentX = getLayoutX();
        double currentY = getLayoutY();

        boatDesign.setRotate(rotated ? 0 : 90); // Rotar 90 grados si es horizontal, sino vertical

        // Recalcular la posición del barco después de la rotación
        placeBoat(boatDesign, currentX, currentY, length, isHorizontal);

        // Se asegura de que el barco quede alineado correctamente después de la rotación
        double offsetX = currentX - getLayoutX();
        double offsetY = currentY - getLayoutY();
        setLayoutX(currentX - offsetX);
        setLayoutY(currentY - offsetY);

        System.out.println("--------");
        boardHandler.printBoard();
        System.out.println("Boat rotated.");
        System.out.println("Placing boat at X: " + getLayoutX() + ", Y: " + getLayoutY() + ", Horizontal: " + isHorizontal);
        boardHandler.printBoard();
        System.out.println("--------");
    }



    public boolean isWasFirstMove() {
        return wasFirstMove;
    }

    public void setWasFirstMove(boolean wasFirstMove) {
        this.wasFirstMove = wasFirstMove;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean wasRotated) {
        rotated = wasRotated;
    }


}
