package com.mygdx.game.GUI.background;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Utility.Manager;

public abstract class BackgroundSet {
    private BackgroundGradient[] backgroundGradients;

    public BackgroundSet(BackgroundGradient[] backgroundGradients) {
        this.backgroundGradients = backgroundGradients;
    }

    public void draw(ShapeRenderer shapeRenderer){

        // Draw backgroundGradients
        for(BackgroundGradient bg : backgroundGradients){
            bg.draw(shapeRenderer, Manager.character.getCorrectedHeight());
        }
    }

    public abstract void update();
}
