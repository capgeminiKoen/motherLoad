package com.mygdx.game.GUI.buildingscreens;

import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.inventory.items.Engine;
import com.mygdx.game.inventory.items.UpgradeItem;

public class EngineMenu extends ItemScreen {

    @Override
    protected UpgradeItem[] getUpgradeItems() {
        return Engine.values();
    }

    public EngineMenu() {
        title = "Engines";
        valueType = "Engine";
    }

    public EngineMenu(int width, int height, Anchor anchor) {
        super(width, height, anchor);
        title = "Engines";
        valueType = "Engine";
    }

    public EngineMenu(int width, int height, Anchor anchor, int left, int right, int top, int down) {
        super(width, height, anchor, left, right, top, down);
        title = "Engines";
        valueType = "Engine";
    }
}
