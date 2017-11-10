package com.mygdx.game;

public class Coordinate {
    int x, y;

    public Coordinate(int newX, int newY) {
        x = newX;
        y = newY;


    }

    @Override
    public String toString() {
        return "X: " + x + ", Y:" + y;
    }
}
