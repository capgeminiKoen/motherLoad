package com.mygdx.game.GUI.buildingscreens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.GUI.Screen;
import com.mygdx.game.Manager;
import com.mygdx.game.inventory.items.UpgradeItem;

public abstract class ItemScreen extends Screen {

    private int rowHeight = 40;
    private UpgradeItem[] upgradeItems;
    protected String valueType;

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

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    protected abstract UpgradeItem[] getUpgradeItems();

    public void drawOptions(SpriteBatch batch){

        // Return if we have no upgradeItems
        if(upgradeItems.length == 0) return;

        // Get width per column
        int columnWidth = width / 3;

        Vector2 currentPosition = new Vector2(rect.x, rect.y + rect.height - rowHeight);

        // Get value type
        String itemType = valueType;

        drawRow(batch, currentPosition, columnWidth, "Price", "UpgradeItem", itemType);

        // Decrement position
        currentPosition.y -= rowHeight;

        // Start drawing from top left
        for (int i = 0; i < upgradeItems.length; i++) {
            String name = upgradeItems[i].getItem().getName();
            int price = upgradeItems[i].getItem().getPrice();
            String value = upgradeItems[i].getProperties();
            // Draw this row
            drawRow(batch, currentPosition, columnWidth, name, "" + price, value);
        }

    }

    // Draw a single menu row
    private void drawRow(SpriteBatch batch, Vector2 currentPosition, int columnWidth, String first, String second, String third){
        // Draw first row
        Manager.font.draw(batch, first, currentPosition.x, currentPosition.y);
        Manager.font.draw(batch, second, currentPosition.x + columnWidth, currentPosition.y);
        Manager.font.draw(batch, third, currentPosition.x + columnWidth * 2, currentPosition.y);
        currentPosition.y -= rowHeight;
    }

}
