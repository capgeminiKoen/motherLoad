package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.resources.Resource;

import static com.mygdx.game.Manager.font;

public class InventoryScreen extends Screen {

    private int tileSize = 64;
    private int rows = 5, cols = 5;
    private int bottomSize = 50;
    private Inventory inventory;

    public InventoryScreen(){
        width = tileSize * cols;
        height = bottomSize + tileSize * rows + topSize;
        title = "Inventory";
        anchor = Anchor.DOWN_RIGHT;
        initializeRect();
    }

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        // Draw grid
        drawInventoryFrame(batch, Manager.screenSize.x - width, bottomSize);

        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);

        // Draw title
        font.draw(batch, title, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2 + height);

        // Draw inventoryItems that are present in the inventory
        drawInventoryItems(batch, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2 + bottomSize);

        // Draw details
        drawInventoryDetails(batch, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2 + bottomSize);

        // Set original matrix back again
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    private void drawInventoryDetails(SpriteBatch batch, float x, float y){
        int index = 0;
        font.draw(batch, "Money: " + Manager.character.getMoney(), x, y - index++ * Manager.fontSize);
        font.draw(batch, "Weight:" + inventory.getResourceWeight() + "/" + inventory.getTrunkSize(), x, y - index++ * Manager.fontSize);
        font.draw(batch, "Current inventory value: " + inventory.getCurrentInventoryValue(), x, y - index++ * Manager.fontSize);
    }

    private void drawInventoryItems(SpriteBatch batch, float x, float y){
        int currentIndex = 0;
        int[] resources = inventory.getResources();
        Resource[] resourceInstances = Resource.values();
        for (int i = 0; i < resources.length; i++) {
            if(resources[i] > 0){
                batch.draw(resourceInstances[i].texture, x + tileSize * currentIndex, y, tileSize, tileSize);
                font.draw(batch, resources[i] + "X", x + tileSize * currentIndex,y + 15);
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
