package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Utility.Coordinate;
import com.mygdx.game.GUI.menus.WareHouseMenu;
import com.mygdx.game.Utility.Manager;

public class Hud {

    private int barWidth = 250, barHeight = 10;
    private Coordinate healthBarPos = new Coordinate(10,10);
    private Coordinate fuelBarPos = new Coordinate(10,20);
    private float hitEffectLength = 0.2f, currentHitEffectTime = 0.0f;
    private boolean isShowingHitEffect = false;
    private ScreenType currentScreen = ScreenType.None;

    public void draw(SpriteBatch batch){
        // Draw the Hud here
        drawHealthBar(batch);
        drawFuelBar(batch);

        // Show hit effect
        if(isShowingHitEffect){
            drawHitEffect(batch);
        }

        // Draw Screen
        if(currentScreen != ScreenType.None) {
            currentScreen.getScreen().draw(batch);
        }

        // draw moneys
        drawMonages(batch);

        // Draw altimeter
        drawAltimeter(batch);
    }

    // Draw the amount of the monages in the bottom center of the screen.
    private void drawMonages(SpriteBatch batch){
        // Set batch's matrix
        batch.setProjectionMatrix(Manager.camera.projection);
        // Draw monages
        Manager.font.draw(batch, "$ " + Manager.character.getMoney() + ",-", - 50, -Manager.screenSize.y / 2 + 35);
        // Set batch's matrix
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    // Draw the height we're at
    private void drawAltimeter(SpriteBatch batch){
        // Set batch's matrix
        batch.setProjectionMatrix(Manager.camera.projection);
        // Draw monages
        Manager.font.draw(batch, String.format("Height: %.2f m", (Manager.character.getCurrentHeight() / 10)), 100, -Manager.screenSize.y / 2 + 35);
        // Set batch's matrix
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    private void drawHealthBar(SpriteBatch batch){
        Color color = new Color(1,0,0,1);
        float x = Manager.screenSize.x - healthBarPos.x - barWidth;
        float y = healthBarPos.y;
        float percentage = Manager.character.getHealthPercentage();
        drawBar(batch, color, x, y, percentage);
    }

    private void drawFuelBar(SpriteBatch batch){
        Color color = new Color(0,0,1,1);
        float x = Manager.screenSize.x - fuelBarPos.x - barWidth;
        float y = fuelBarPos.y;
        float percentage = Manager.character.getFuelPercentage();
        drawBar(batch, color, x, y, percentage);
    }

    private void drawBar(SpriteBatch batch, Color color, float x, float y, float percentage){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        // Bar itself
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, percentage * barWidth, barHeight);

        // Draw surrounding with greyish color
        shapeRenderer.setColor(new Color(0.6f,0.6f,0.6f,1));
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        // Draw EXACTLY SAME RECT, now with a line only
        shapeRenderer.rect(x, y, barWidth, barHeight);
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

    public boolean isInMenu(){
        return currentScreen != ScreenType.None;
    }

    public void showHitEffect(){
        currentHitEffectTime = hitEffectLength;
        isShowingHitEffect = true;
    }

    public ScreenType getCurrentScreen(){
        return currentScreen;
    }

    public void switchMenu(ScreenType screenType){
        if(currentScreen == screenType){
            return;
        }
        if(screenType.getScreen() instanceof WareHouseMenu){
            // Update the warehouse menu.
            WareHouseMenu menu = (WareHouseMenu)screenType.getScreen();
            menu.changeTo();
        }
        currentScreen = screenType;
    }

    public void toggleMenu(ScreenType screenType){
        if(currentScreen == screenType){
            currentScreen = ScreenType.None;
            return;
        }
        currentScreen = screenType;
    }
}
