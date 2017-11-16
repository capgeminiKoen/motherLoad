package com.mygdx.game.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Coordinate;

public abstract class Building {
    public Texture texture;
    public Coordinate position;

    public Building(String texturePath, Coordinate position){
        texture = new Texture(texturePath);
        this.position = position;
    }
}
