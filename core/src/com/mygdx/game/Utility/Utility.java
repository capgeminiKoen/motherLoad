package com.mygdx.game.Utility;

public final class Utility {
    private Utility(){

    }

    // Index to return when something is not found.
    public static final int NOT_FOUND = -1;

    public static int clamp(int number, int min, int max){
        return (number < min) ? min : ((number > max) ? max : number);
    }
    public static float clamp(float number, float min, float max){
        return (number < min) ? min : ((number > max) ? max : number);
    }

}
