package com.mygdx.game.GUI.buildingscreens;

import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.inventory.items.Trunk;
import com.mygdx.game.inventory.items.UpgradeItem;

public class TrunkMenu extends ItemScreen {

    @Override
    protected UpgradeItem[] getUpgradeItems() {
        return Trunk.values();
    }

    public TrunkMenu() {
        title = "Trunks";
        valueType = "Trunk";
    }

    public TrunkMenu(int width, int height, Anchor anchor) {
        super(width, height, anchor);
        title = "Trunks";
        valueType = "Trunk";
    }

    public TrunkMenu(int width, int height, Anchor anchor, int left, int right, int top, int down) {
        super(width, height, anchor, left, right, top, down);
        title = "Trunks";
        valueType = "Trunk";
    }
}
