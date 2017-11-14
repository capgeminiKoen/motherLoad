package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Manager {
    public  static final float gravity = 200f;
    public static Vector2 screenSize = new Vector2(0,0);
    public static Map map;
    public static final float fallDamageSpeedThreshold = 500.0f;
    public static final Random random = new Random();
    public static final BitmapFont font = new BitmapFont();
    public static Camera camera;
}
