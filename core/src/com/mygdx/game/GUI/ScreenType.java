package com.mygdx.game.GUI;

import com.mygdx.game.GUI.buildingscreens.TankMenu;
import com.mygdx.game.GUI.buildingscreens.TankStationMenu;
import com.mygdx.game.GUI.buildingscreens.WareHouseMenu;

public enum ScreenType {
    None(null),
    Inventory(new InventoryScreen()),
    TankstationMenu(new TankMenu());

    public Screen getScreen() {
        return screen;
    }

    private Screen screen;


    ScreenType(Screen screen){
        this.screen = screen;
    }
}
