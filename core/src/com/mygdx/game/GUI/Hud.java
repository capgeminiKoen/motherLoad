package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Coordinate;
import com.mygdx.game.Manager;

public class Hud {

    private int healthBarWidth, healthBarHeight;
    private Coordinate healthBarPos = new Coordinate(10,10);
    private float hitEffectLength = 0.2f, currentHitEffectTime = 0.0f;
    private boolean isShowingHitEffect = false;
    private ScreenType currentScreen = ScreenType.None;

    //
    public Hud(int healthBarWidth, int healthBarHeight){
        this.healthBarHeight = healthBarHeight;
        this.healthBarWidth = healthBarWidth;
    }

    public void draw(SpriteBatch batch){
        // Draw the Hud here
        drawHealthBar(batch);

        // Show hit effect
        if(isShowingHitEffect){
            drawHitEffect(batch);
        }

        // Draw Screen
        if(currentScreen != ScreenType.None) {
            currentScreen.getScreen().draw(batch);
        }
    }

    private void drawHealthBar(SpriteBatch batch){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        // Bar itself
        Color color = new Color(1,0,0,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(Manager.screenSize.x - healthBarPos.x - healthBarWidth, healthBarPos.y, healthBarWidth * Manager.character.getHealthPercentage(), healthBarHeight);

        shapeRenderer.setColor(new Color(0.6f,0.6f,0.6f,1));
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        // Draw EXACTLY SAME RECT, now with a line only
        shapeRenderer.rect(Manager.screenSize.x - healthBarPos.x - healthBarWidth, healthBarPos.y, healthBarWidth, healthBarHeight);
        shapeRenderer.end();
        // Begin again
        batch.begin();
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

    public void switchMenu(ScreenType screenType){
        if(currentScreen == screenType){
            currentScreen = ScreenType.None;
            return;
        }
        currentScreen = screenType;
    }
}
