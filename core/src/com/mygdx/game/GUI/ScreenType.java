package com.mygdx.game.GUI;

import com.mygdx.game.GUI.buildingscreens.*;
import com.mygdx.game.inventory.items.*;

public enum ScreenType {
    None(null),
    WarehouseMenu(new WareHouseMenu()),
    Inventory(new InventoryScreen()),
    TankstationMenu(new TankStationMenu()),
    TankMenu(new ItemScreen(Tank.values(), "Fuel tanks", "Tank properties"), true),
    EngineMenu(new ItemScreen(Engine.values(), "Engines", "Engine"), true),
    TrunkMenu(new ItemScreen(Trunk.values(), "Trunks", "Trunk Size"), true),
    DrillMenu(new ItemScreen(Drill.values(), "Drills", "Drilling speed"), true),
    HullMenu(new ItemScreen(Hull.values(), "Hulls", "Hull Strength"), true);

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
