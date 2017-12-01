package com.mygdx.game.GUI.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Background {
    BackgroundSet[] backgrounds;

    public Background(BackgroundSet[] backgrounds) {
        this.backgrounds = backgrounds;
    }

    public void draw(SpriteBatch spriteBatch){

        // Draw the background
        spriteBatch.end();

        // Create ShapeRenderer for all our backgroundGradients
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw backgrounds
        for(BackgroundSet backgroundSet : backgrounds){
            backgroundSet.draw(shapeRenderer);
        }

        // End shapeRenderer and begin spritebatch again
        shapeRenderer.end();
        spriteBatch.begin();
    }

    public void update(){
        for(BackgroundSet backgroundSet : backgrounds){
            backgroundSet.update();
        }
    }
}
