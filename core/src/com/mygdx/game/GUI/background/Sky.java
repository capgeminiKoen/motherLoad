package com.mygdx.game.GUI.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Manager;

/**
 * A sky class consists of different backgrounds which all
 */
public class Sky {

    private Background[] backgrounds;

    public Sky (Background[] backgrounds){
        this.backgrounds = backgrounds;
    }

    public void draw(SpriteBatch spriteBatch){

        // Draw the background
        spriteBatch.end();

        // Create ShapeRenderer for all our backgrounds
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw backgrounds
        for(Background bg : backgrounds){
            bg.draw(shapeRenderer, Manager.character.getCurrentHeight());
        }

        // End shapeRenderer and begin spritebatch again
        shapeRenderer.end();
        spriteBatch.begin();
    }

}
