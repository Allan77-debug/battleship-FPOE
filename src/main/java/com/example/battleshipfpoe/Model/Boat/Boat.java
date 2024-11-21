package com.example.battleshipfpoe.Model.Boat;

import com.example.battleshipfpoe.Controller.GameController;
import com.example.battleshipfpoe.Controller.MenuController;
import com.example.battleshipfpoe.Model.Board.BoardHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Boat extends Group implements BoatInterface {

    private static final int SQUARE_SIZE = 60; // Size of one square
    private int length;
    private boolean isHorizontal;
    private double startX, startY;
    private int currentRow = -1;
    private int currentCol = -1;
    private BoardHandler boardHandler;
    private boolean rotated = false;

    private boolean wasFirstMove = true;


    public Boat(double startX, double startY, int length, boolean isHorizontal) {
        this.startX = startX;
        this.startY = startY;
        this.length = length;
        this.isHorizontal = isHorizontal;
        // Call placeBoat to initialize the boat
        placeBoat(startX, startY, length, isHorizontal);

        setupInteractions();
    }



    @Override
    public void placeBoat(double startX, double startY, int length, boolean isHorizontal) {
        // Loop to create the boat's squares based on its length
            for (int i = 0; i < length; i++) {
                // Create a rectangle for each square of the boat
                Rectangle rect = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                rect.setFill(Color.LIGHTGREEN);
                rect.setStroke(Color.BLACK);

                // Position each rectangle based on whether the boat is horizontal or vertical
                if (isHorizontal) {
                    rect.setLayoutX(i * SQUARE_SIZE); // Offset each square horizontally
                    rect.setLayoutY(0); // Keep Y constant for horizontal
                } else {
                    rect.setLayoutX(0); // Keep X constant for vertical
                    rect.setLayoutY(i * SQUARE_SIZE); // Offset each square vertically
                }

                // Add each square to the Group
                getChildren().add(rect);
            }

            // Set the initial position of the boat
            setLayoutX(startX);
            setLayoutY(startY);
            toFront(); // Ensure the boat is at the front of the scene
        }


    public void storePosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
        System.out.println("Snapped at row: " + currentRow + ", col: " + currentCol);
    }
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


    public void updateBoatPosition(BoardHandler boardHandler) {
        for (int i = 0; i < length; i++) {
            if (isHorizontal) {
                boardHandler.setCell(currentRow, currentCol + i, 1);
            } else {
                boardHandler.setCell(currentRow + i, currentCol, 1);
            }
        }
    }

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

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public int[] getPosition() {
        return new int[]{currentRow, currentCol};
    }

    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    private void setupInteractions() {
        this.setOnMousePressed(event -> {
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


    public void rotate() {
        rotated = !rotated;

        isHorizontal = !isHorizontal;

        this.getChildren().clear();
        placeBoat(getLayoutX(), getLayoutY(), length, isHorizontal);

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
