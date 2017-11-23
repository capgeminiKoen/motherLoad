package com.mygdx.game.GUI.buildingscreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GUI.Screen;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.resources.Resource;

public class WareHouseMenu extends Screen {

    // Get width per column
    int columnWidth;
    int rowHeight = 40;
    int currentItem = 1;
    int resourceAmount[];
    Resource[] resources;

    // Standard window is 500x400.
    public WareHouseMenu() {
        title = "Warehouse";
        columnWidth = width / 5;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        // Now add text and stuff
        drawTitle(batch);

        // Draw some info
        drawInfo(batch);

        // Get the resourceamount and resources.
        // TODO: This can be done once, but NOT in the constructor!!
        resourceAmount = Manager.character.getInventory().getResources();
        resources = Resource.values();

        // Draw resources
        drawResources(batch);
    }

    // draw all resources
    public void drawResources(SpriteBatch batch){

        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);

        // NOTE: Correct the text for center of screen!
        Vector2 currentPosition = new Vector2(rect.x - Manager.screenSize.x / 2, rect.y + rect.height - rowHeight * 2 - Manager.screenSize.y / 2);

        // Draw a single row. (auto decrements)
        drawRow(batch, currentPosition, "Item", "Amount", "Price", "Weight", "Total", -1);

        // Start drawing from top left
        for (int i = 0; i < resourceAmount.length; i++) {
            String name;
            float weight;
            int value, total, amount;
            if(resourceAmount[i] > 0){
                name = resources[i].name;
                value = resources[i].value;
                amount = resourceAmount[i];
                total = value * amount;
                weight = resources[i].weight;
                // Draw this row
                drawRow(batch, currentPosition, name, "" + amount, "" +value, "" +weight, "" + total, i);
            }
        }

        // Reset projection matrix
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    // Draw a single menu row
    private void drawRow(SpriteBatch batch, Vector2 currentPosition, String first, String second, String third, String fourth, String fifth, int itemNumber){
        // Do something when we draw the current selected item
        if(itemNumber == currentItem){
            Manager.font.setColor(1,0,0,1);
        }
        // Draw first row
        Manager.font.draw(batch, first, currentPosition.x, currentPosition.y);
        Manager.font.draw(batch, second, currentPosition.x + columnWidth, currentPosition.y);
        Manager.font.draw(batch, third, currentPosition.x + columnWidth * 2, currentPosition.y);
        Manager.font.draw(batch, fourth, currentPosition.x + columnWidth * 3, currentPosition.y);
        Manager.font.draw(batch, fifth, currentPosition.x + columnWidth * 4, currentPosition.y);
        currentPosition.y -= rowHeight;
        if(itemNumber == currentItem){
            Manager.font.setColor(1,1,1,1);
        }
    }

    // Draw items in rows.
    void drawInfo(SpriteBatch batch){
        batch.setProjectionMatrix(Manager.camera.projection);

        Vector2 currentPos = new Vector2(-width / 2 + 10, height / 2 - 20);
        float rowDist = 15;

        // Draw some info
        Manager.font.draw(batch, "Enter: sell",currentPos.x, currentPos.y);
        // Increment position's y
        currentPos.y -= rowDist;
        // Draw some info
        Manager.font.draw(batch, "CTRL+ENTER: sell all of currently selected resource",currentPos.x, currentPos.y);
        // Increment position's y
        currentPos.y -= rowDist;
        // Draw some info
        Manager.font.draw(batch, "SHIFT+Enter: sell all.",currentPos.x, currentPos.y);

        batch.setProjectionMatrix(Manager.camera.combined);
    }

    private void incrementItem(int numberOfItems){
        currentItem++;
        if(currentItem >= numberOfItems){
            currentItem = 0;
        }
    }
    private void decrementItem(int numberOfItems){
        currentItem--;
        if(currentItem < 0){
            currentItem = numberOfItems - 1;
        }
    }

    private void changeItem(boolean incremental){
        int numberOfItems = resourceAmount.length;
        int index = 0;

        // loop to the next item
        while(index < numberOfItems){
            // Change the itemnumber
            if(incremental){
                incrementItem(numberOfItems);
            }
            else{
                decrementItem(numberOfItems);
            }
            // If this actually contains an item, return
            if(resourceAmount[currentItem] > 0){
                return;
            }
            index++;
        }
    }

    public void nextItem(){
        changeItem(true);
    }

    public void previousItem(){
        changeItem(false);
    }

    // Return currently selected resource
    public Resource getSelectedResource(){
        return resources[currentItem];
    }
}
