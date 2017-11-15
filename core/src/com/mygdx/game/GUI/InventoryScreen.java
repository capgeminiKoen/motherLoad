package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.resources.Resource;

public class InventoryScreen extends Screen {

    private int tileSize = 64;
    private int rows = 5, cols = 5;
    private int width = tileSize * cols, height = tileSize * rows + 20;
    private String title = "Inventory";
    private Inventory inventory;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Draw background
        drawBackground(batch);

        // Draw grid
        drawInventoryFrame(batch, Manager.screenSize.x - width, 0);

        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);
        // Draw title
        Manager.font.draw(batch, title, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2 + height);

        // Draw inventoryItems that are present in the inventory
        drawInventoryItems(batch, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2);

        // Set original matrix back again
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    private void drawBackground(SpriteBatch batch){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        // Bar itself
        Color color = new Color(0.4f,0.4f,0.4f,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);

        // Draw rect
        shapeRenderer.rect(Manager.screenSize.x - width, 0, width, height);

        shapeRenderer.end();

        // Start batch again
        batch.begin();
    }

    private void drawInventoryItems(SpriteBatch batch, float x, float y){
        int currentIndex = 0;
        int[] resources = inventory.getResources();
        Resource[] resourceInstances = Resource.values();
        for (int i = 0; i < resources.length; i++) {
            if(resources[i] > 0){
                batch.draw(resourceInstances[i].texture, x + tileSize * currentIndex, y, tileSize, tileSize);
                Manager.font.draw(batch, resources[i] + "X", x + tileSize * currentIndex,y + 15);
                currentIndex++;
            }
        }
    }

    private void drawInventoryFrame(SpriteBatch batch, float x, float y){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        // Bar itself
        Color color = new Color(0.2f,0.2f,0.2f,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                shapeRenderer.rect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);
            }
        }

        shapeRenderer.end();

        // Start batch again
        batch.begin();


    }
}
