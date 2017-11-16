package com.mygdx.game.buildings;

import com.mygdx.game.Coordinate;

public enum BuildingType {
    Warehouse(new Warehouse("warehouse.png", new Coordinate(100, 0))),
    TankStation(new TankStation("tankstation.png", new Coordinate(350, 0)));

    public Building building;

    BuildingType(Building building){
        this.building = building;
    }
}
