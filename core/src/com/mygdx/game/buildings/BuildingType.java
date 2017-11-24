package com.mygdx.game.buildings;

import com.mygdx.game.Coordinate;
import com.mygdx.game.GUI.Anchor;
import com.mygdx.game.GUI.InventoryScreen;
import com.mygdx.game.GUI.ScreenType;

public enum BuildingType {

    Warehouse(new Warehouse("warehouse.png", new Coordinate(150, 0)), ScreenType.WarehouseMenu),
    TankStation(new TankStation("tankstation.png", new Coordinate(500, 0)), ScreenType.TankstationMenu),
    TankShop(new TankShop("tankshop.png", new Coordinate(1000,0)), ScreenType.TankMenu),
    EngineShop(new Building("engineShop.png", new Coordinate(1600, 0)), ScreenType.EngineMenu);

    public Building building;
    public ScreenType screenType;

    BuildingType(Building building, ScreenType screenType) {
        this.building = building;
        this.screenType = screenType;
    }
}
