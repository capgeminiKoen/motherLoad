package com.mygdx.game.buildings;

import com.mygdx.game.Utility.Coordinate;
import com.mygdx.game.GUI.ScreenType;

public enum BuildingType {

    Warehouse(new Warehouse("warehouse.png", new Coordinate(150, 0)), ScreenType.WarehouseMenu),
    TankStation(new TankStation("tankstation.png", new Coordinate(500, 0)), ScreenType.TankstationMenu),
    TankShop(new TankShop("tankshop.png", new Coordinate(1000,0)), ScreenType.TankMenu),
    EngineShop(new Building("engineShop.png", new Coordinate(1600, 0)), ScreenType.EngineMenu),
    TrunkShop(new Building("trunkShop.png", new Coordinate(2200, 0)), ScreenType.TrunkMenu),
    DrillShop(new Building("drillShop.png", new Coordinate(2500, 0)), ScreenType.DrillMenu),
    HullShop(new Building("hullShop.png", new Coordinate(3000, 0)), ScreenType.HullMenu);

    public Building building;
    public ScreenType screenType;

    BuildingType(Building building, ScreenType screenType) {
        this.building = building;
        this.screenType = screenType;
    }
}
