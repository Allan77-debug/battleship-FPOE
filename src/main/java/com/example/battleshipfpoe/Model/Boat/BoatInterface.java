package com.example.battleshipfpoe.Model.Boat;

public interface BoatInterface {
    void placeBoat(double startX, double startY, int length, boolean isHorizontal);
    int getLength();
    double getStartX();
    double getStartY();
    boolean isHorizontal();
}

