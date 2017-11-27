package com.mygdx.game.GUI.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Manager;

/**
 * Background class -> Draws nice ass backgrounds.
 */
public class Background {

    private Color colors[] = new Color[4];
    private float y, height;

    public Background(float start, float end){
        // make a rectangle
        this.y = start;
        this.height = end - start;
    }

    public Background(float y, float height, Color color1, Color color2){
        this(y, height);
        colors[0] = colors[1] = color1;
        colors[2] = colors[3] = color2;
    }

    public Background(float y, float height, Color color1, Color color2, Color color3, Color color4){
        this(y, height);
        colors[0] = color1;
        colors[1] = color2;
        colors[2] = color3;
        colors[3] = color4;
    }

    public void draw(ShapeRenderer shapeRenderer, float characterHeight){
        // Height that we receive is the center of the screen. Check whether we have some overlap with the screen
        // container.
        float top = characterHeight + Manager.screenSize.y / 2;
        float bottom = characterHeight - Manager.screenSize.y / 2;

        // If this background is outside the screen, return
        if(top < y && bottom > y + characterHeight ){
            return;
        }

        // Draw gradient :)
        shapeRenderer.rect(0, y - characterHeight + Manager.screenSize.y / 2, Manager.screenSize.x, height, colors[0], colors[1], colors[2], colors[3]);
    }

}
