package com.example.battleshipfpoe.View;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Boat {

    private final Group boat; // Agrupa los elementos visuales del barco
    private boolean isHorizontal = true; // Orientación del barco

    public Boat() {
        // Crear el casco del barco usando elipses
        Ellipse cascoExterior = new Ellipse(50.0, 25.0, 40.0, 20.0);
        cascoExterior.setFill(Color.web("#906d6d"));
        cascoExterior.setStroke(Color.BLACK);

        Ellipse cascoInterior = new Ellipse(50.0, 25.0, 35.0, 15.0);
        cascoInterior.setFill(Color.WHITE);
        cascoInterior.setStroke(Color.BLACK);

        // Crear el mástil y las velas usando rectángulos
        Rectangle mastil = new Rectangle(45.0, 10.0, 10.0, 30.0);
        mastil.setFill(Color.web("#ccd1d7"));
        mastil.setStroke(Color.BLACK);

        boat = new Group(cascoExterior, cascoInterior, mastil);

        // Agregar eventos al barco
        setupInteractions();
    }

    public Group getBoat() {
        return boat;
    }

    // Alternar orientación del barco
    private void toggleOrientation() {
        isHorizontal = !isHorizontal;
        boat.setRotate(isHorizontal ? 0 : 90);
    }

    // Configurar eventos de interacción
    private void setupInteractions() {
        // Arrastre del barco
        boat.setOnMouseDragged(event -> {
            boat.setLayoutX(event.getSceneX() - 50); // Ajustar posición X
            boat.setLayoutY(event.getSceneY() - 25); // Ajustar posición Y
        });

        // Rotación con tecla R
        boat.setOnKeyPressed(event -> handleRotation(event));
        boat.setFocusTraversable(true); // Necesario para capturar eventos de teclado
    }

    private void handleRotation(KeyEvent event) {
        if (event.getCode() == KeyCode.R) {
            toggleOrientation();
        }
    }
}