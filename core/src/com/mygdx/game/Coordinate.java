package com.mygdx.game;

public class Coordinate {
    public int x, y;

    public Coordinate(int newX, int newY) {
        x = newX;
        y = newY;


    }

    @Override
    public String toString() {
        return "X: " + x + ", Y:" + y;
    }
}
