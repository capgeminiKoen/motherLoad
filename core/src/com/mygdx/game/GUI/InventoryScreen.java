package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.resources.Resource;

public class InventoryScreen extends Screen {

    private int tileSize = 64;
    private int width = tileSize * 5, height = tileSize * 10 + 20;
    private String title = "Inventory";
    private Inventory inventory;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);
        // Draw title
        Manager.font.draw(batch, title, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2 + height);

        // Draw inventoryItems that are present in the inventory
        drawInventoryItems(batch, Manager.screenSize.x / 2 - width, - Manager.screenSize.y / 2);

        // Set original matrix back again
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    private void drawInventoryItems(SpriteBatch batch, float x, float y){
        int currentIndex = 0;
        int[] resources = inventory.getResources();
        Resource[] resourceInstances = Resource.values();
        for (int i = 0; i < resources.length; i++) {
            if(resources[i] > 0){
                batch.draw(resourceInstances[i].texture, x + tileSize * currentIndex, y);
                currentIndex++;
            }
        }
    }
}
