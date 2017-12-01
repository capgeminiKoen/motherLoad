package com.mygdx.game.GUI.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utility.Manager;

/**
 * Draw a sun in the background as part of the sky.
 * @author kgriffio
 */
public class Sun {

    private float maxHeight = 400.0f;
    private float minWidth = 0.2f; // Procentual x start position
    private float maxWidth = 0.8f; // Procentual x end pos
    private float size = 50.0f;


    /**
     *
     * @param shapeRenderer
     * @param characterHeight
     * @param characterWidth
     * @param timeOfDay Time of day describes the current daytime. 0.0f is early morning, then the sun starts to set.
     *                  The sun does not show up when the time of day is > 0.5f, then we will just return.
     */
    public void draw(ShapeRenderer shapeRenderer, float characterHeight, float characterWidth, float timeOfDay){

        if(timeOfDay > 0.5f || timeOfDay < 0) return;
        // Height that we receive is the center of the screen. Check whether we have some overlap with the screen
        // container.
        float top = characterHeight + Manager.screenSize.y / 2;
        float bottom = characterHeight - Manager.screenSize.y / 2;
        float left = characterWidth + Manager.screenSize.x / 2;
        float right = characterWidth - Manager.screenSize.x / 2;

        // Calculate Sun's position
        Vector2 position = calculatePosition(timeOfDay);

        // If this sun of a bitch is outside the screen, return
        if((top < position.y && bottom > position.y ) || (left < position.x && right > position.x)) {
            return;
        }

        shapeRenderer.setColor(new Color(1,1,1,1));
        // Draw sun :)
        shapeRenderer.circle(position.x - characterWidth + Manager.screenSize.x / 2, position.y - bottom, size);
    }
    public void draw(ShapeRenderer shapeRenderer, Vector2 characterPos, float timeOfDay){
        draw(shapeRenderer, characterPos.y, characterPos.x, timeOfDay);
    }

    public Vector2 calculatePosition(float timeOfDay){

        Vector2 position = new Vector2();
        position.x = minWidth + (maxWidth - minWidth) * timeOfDay * 2; // *2 to map to [0 1]
        // Multiply this pos with the map's width
        position.x *= Manager.map.pixelWidth;

        // Get Height
        // Map the time of day of 0 and 0.5f to [-1 +1]
        timeOfDay *= 4;
        timeOfDay -= 1;
        // timeOfDay ^2 is now mapped to [0-1]
        float heightMultiplier = - (timeOfDay * timeOfDay) + 1;
        position.y = maxHeight * heightMultiplier - size * 2;

        return position;
    }


}
