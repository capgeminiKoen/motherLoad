package com.mygdx.game.buildings;

import com.mygdx.game.Coordinate;
import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.GUI.InventoryScreen;
import com.mygdx.game.GUI.ScreenType;

public enum BuildingType {
    Warehouse(new Warehouse("warehouse.png", new Coordinate(150, 0)), ScreenType.WareHouseMenu),
    TankStation(new TankStation("tankstation.png", new Coordinate(500, 0)), ScreenType.TankstationMenu);

    public Building building;
    public ScreenType screenType;

    BuildingType(Building building, ScreenType screenType) {
        this.building = building;
        this.screenType = screenType;
    }
}
