package com.mygdx.game.GUI;

import com.mygdx.game.GUI.buildingscreens.EngineMenu;
import com.mygdx.game.GUI.buildingscreens.TankMenu;
import com.mygdx.game.GUI.buildingscreens.TankStationMenu;
import com.mygdx.game.GUI.buildingscreens.WareHouseMenu;

public enum ScreenType {
    None(null),
    WarehouseMenu(new WareHouseMenu()),
    Inventory(new InventoryScreen()),
    TankstationMenu(new TankStationMenu()),
    TankMenu(new TankMenu(), true),
    EngineMenu(new EngineMenu(), true);

    public Screen getScreen() {
        return screen;
    }

    public boolean isItemScreen() {
        return itemScreen;
    }

    private Screen screen;
    private boolean itemScreen = false;

    ScreenType(Screen screen){
        this.screen = screen;
    }

    ScreenType(Screen screen, boolean itemScreen){
        this.screen = screen;
        this.itemScreen = itemScreen;
    }
}
