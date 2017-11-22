package com.mygdx.game.GUI.buildingscreens;

import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.inventory.items.Hull;
import com.mygdx.game.inventory.items.UpgradeItem;

public class HullMenu extends ItemScreen{

    public HullMenu() {
        title = "Hull";
        valueType = "Hull properties";
    }

    public HullMenu(int width, int height, Anchor anchor) {
        super(width, height, anchor);
        title = "Hull";
        valueType = "Hull properties";
    }

    public HullMenu(int width, int height, Anchor anchor, int left, int right, int top, int down) {
        super(width, height, anchor, left, right, top, down);
        title = "Hull";
        valueType = "Hull properties";
    }

    @Override
    protected UpgradeItem[] getUpgradeItems() {
        return Hull.values();
    }
}
