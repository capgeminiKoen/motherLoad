package com.mygdx.game.GUI.buildingscreens;

import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.inventory.items.Tank;
import com.mygdx.game.inventory.items.UpgradeItem;

public class TankMenu extends ItemScreen {

    public TankMenu() {
        title = "Fuel tanks";
        valueType = "Tank properties";
    }

    public TankMenu(int width, int height, Anchor anchor) {
        super(width, height, anchor);
        title = "Fuel tanks";
        valueType = "Tank properties";
    }

    public TankMenu(int width, int height, Anchor anchor, int left, int right, int top, int down) {
        super(width, height, anchor, left, right, top, down);
        title = "Fuel tanks";
        valueType = "Tank properties";
    }

    @Override
    protected UpgradeItem[] getUpgradeItems() {
        return Tank.values();
    }
}
