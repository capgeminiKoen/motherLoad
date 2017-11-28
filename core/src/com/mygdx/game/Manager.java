package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.character.Character;

import java.util.Random;

public class Manager {
    public  static final float gravity = 200f;
    public static Vector2 screenSize = new Vector2(0,0);
    public static Map map;
    public static final float fallDamageSpeedThreshold = 500.0f;
    public static final Random random = new Random();
    public static final int fontSize = 15;
    public static final BitmapFont font = new BitmapFont();
    public static Camera camera;
    public static Character character;
    public static float fuelCost = 5.0f;
    public static float minFuelAmount = 5.0f;
    public static Animation<TextureRegion> explosionAnimation;
}
