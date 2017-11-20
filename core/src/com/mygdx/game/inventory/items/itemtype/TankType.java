package com.mygdx.game.inventory.items.itemtype;

import com.mygdx.game.inventory.items.Tank;

public enum TankType {

    Normal(new Tank(0, "Normal Tank", 30)),
    Medium(new Tank(1000, "Medium Tank", 50)),
    Large(new Tank(2500, "Large Tank", 70)),
    Extreme(new Tank(7500, "Extreme Tank", 100));

    public Tank tank;
    TankType(Tank tank){
        this.tank = tank;
    }


}
