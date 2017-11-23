package com.mygdx.game.GUI.buildingscreens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.GUI.Screen;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.items.UpgradeItem;

public abstract class ItemScreen extends Screen {

    private int rowHeight = 40;
    protected UpgradeItem[] upgradeItems;
    protected String valueType;
    protected int currentItem = 0;

    public ItemScreen() {
        super();
        upgradeItems = getUpgradeItems();
    }

    public ItemScreen(int width, int height, Anchor anchor) {
        super(width, height, anchor);
        upgradeItems = getUpgradeItems();
    }

    public ItemScreen(int width, int height, Anchor anchor, int left, int right, int top, int down) {
        super(width, height, anchor, left, right, top, down);
        upgradeItems = getUpgradeItems();
    }

    public void nextItem(){
        currentItem++;
        if(currentItem >= upgradeItems.length)
            currentItem = 0;
    }

    public void previousItem(){
        currentItem--;
        if(currentItem == -1){
            currentItem = upgradeItems.length -1;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        drawOptions(batch);
        drawTitle(batch);
    }

    protected abstract UpgradeItem[] getUpgradeItems();

    // Get currently selected item.
    public UpgradeItem getCurrentlySelectedItem(){
        if(upgradeItems.length == 0){
            return null;
        }
        return upgradeItems[currentItem];
    }

    public void drawOptions(SpriteBatch batch){

        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);

        // Return if we have no upgradeItems
        if(upgradeItems.length == 0) return;

        // Get width per column
        int columnWidth = width / 4;

        // NOTE: Correct the text for center of screen!
        Vector2 currentPosition = new Vector2(rect.x - Manager.screenSize.x / 2, rect.y + rect.height - rowHeight - Manager.screenSize.y / 2);

        // Get value type
        String itemType = valueType;

        // Draw a single row. (auto decrements)
        drawRow(batch, currentPosition, columnWidth, "Price", "Description", itemType, "Available", -1);

        // Start drawing from top left
        for (int i = 0; i < upgradeItems.length; i++) {
            String name = upgradeItems[i].getItem().getName();
            int price = upgradeItems[i].getItem().getPrice();
            String value = upgradeItems[i].getProperties();
            String available = upgradeItems[i].getItem().isAvailable() ? "Bought" : "";
            // Draw this row
            drawRow(batch, currentPosition, columnWidth, "" + price, name, value, available, i);
        }

        // Reset projection matrix
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    // Draw a single menu row
    private void drawRow(SpriteBatch batch, Vector2 currentPosition, int columnWidth, String first, String second, String third, String fourth, int itemNumber){
        // Do something when we draw the current selected item
        if(itemNumber == currentItem){
            Manager.font.setColor(1,0,0,1);
        }
        // Draw first row
        Manager.font.draw(batch, first, currentPosition.x, currentPosition.y);
        Manager.font.draw(batch, second, currentPosition.x + columnWidth, currentPosition.y);
        Manager.font.draw(batch, third, currentPosition.x + columnWidth * 2, currentPosition.y);
        Manager.font.draw(batch, fourth, currentPosition.x + columnWidth * 3, currentPosition.y);
        currentPosition.y -= rowHeight;
        if(itemNumber == currentItem){
            Manager.font.setColor(1,1,1,1);
        }
    }

}
