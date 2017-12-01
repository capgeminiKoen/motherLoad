package com.mygdx.game.GUI.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Utility.Manager;

/**
 * A sky class consists of different backgroundGradients which all
 */
public class Sky extends BackgroundSet{

    private BackgroundGradient[] backgroundGradients;
    private Sun sun;
    private float timeOfDay = 0.0f;
    private float dayLengthInMinutes = 2.0f;

    public Sky(BackgroundGradient[] backgroundGradients) {
        super(backgroundGradients);
        sun = new Sun();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer){
        super.draw(shapeRenderer);
        // Draw sun
        sun.draw(shapeRenderer, Manager.getCorrectedCameraPosition(), timeOfDay);
    }

    /**
     * Update function. updates time.
     */
    @Override
    public void update(){
        timeOfDay += Gdx.graphics.getDeltaTime() / (dayLengthInMinutes * 60);
        timeOfDay %= 1.0f;
    }

}
