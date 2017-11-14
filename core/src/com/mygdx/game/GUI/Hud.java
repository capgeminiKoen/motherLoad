package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Manager;

public class Hud {

    private int healthBarWidth, healthBarHeight;
    private float hitEffectLength = 0.2f, currentHitEffectTime = 0.0f;
    private boolean isShowingHitEffect = false;

    public Hud(int width, int height){
        this.healthBarHeight = height;
        this.healthBarWidth = width;
    }

    public void draw(SpriteBatch batch, float health){
        // Draw the Hud here
        if(isShowingHitEffect){
            drawHitEffect(batch);
        }
    }

    private void drawHitEffect(SpriteBatch batch){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Set alpha based on the amount of time passed.
        Color color = new Color();
        color.set(1,0,0, currentHitEffectTime / hitEffectLength);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(0,0, Manager.screenSize.x, Manager.screenSize.y);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Begin again
        batch.begin();

        // Decrement hit time
        currentHitEffectTime -= Gdx.graphics.getDeltaTime();
        if(currentHitEffectTime < 0){
            currentHitEffectTime = 0;
            isShowingHitEffect = false;
        }

        return;
    }

    public void showHitEffect(){
        currentHitEffectTime = hitEffectLength;
        isShowingHitEffect = true;
    }
}
