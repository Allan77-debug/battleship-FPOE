package com.example.battleshipfpoe.Model.Boat;

import javafx.scene.Group;

public interface BoatInterface {
    void placeBoat(Group BoatDesign, double startX, double startY, int length, boolean isHorizontal);
    int getLength();
    double getStartX();
    double getStartY();
    boolean isHorizontal();
}

