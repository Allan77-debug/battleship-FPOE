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
    private Group boatDesign;


    public Boat(Group boatDesign, double startX, double startY, int length, boolean isHorizontal) {
        this.startX = startX;
        this.startY = startY;
        this.length = length;
        this.isHorizontal = true;
        this.boatDesign = boatDesign;
        // Call placeBoat to initialize the boat
        placeBoat(boatDesign, startX, startY, length, isHorizontal);

        setupInteractions();
    }



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
