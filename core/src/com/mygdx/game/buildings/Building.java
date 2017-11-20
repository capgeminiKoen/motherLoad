package com.mygdx.game.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Coordinate;
import com.mygdx.game.GUI.ScreenType;
import com.mygdx.game.Manager;

public abstract class Building {
    public Texture texture;
    public Coordinate position;

    public Building(String texturePath, Coordinate position){
        texture = new Texture(texturePath);
        this.position = position;
    }
    // Returns true if position is contained
    public boolean containsPosition(Vector2 pos) {
        Vector2 realPosition = new Vector2(position.x, Manager.map.getVerticalMapBounds().y + position.y);
        return (pos.x > realPosition.x && pos.x < realPosition.x + texture.getWidth()) &&
                (pos.y > realPosition.y && pos.y < realPosition.y + texture.getHeight());
    }

    // draw function
    public void draw(SpriteBatch batch){
        batch.draw(texture,
                position.x,
                position.y + Manager.map.getVerticalMapBounds().y,
                texture.getWidth(),
                texture.getHeight());
    }
}
