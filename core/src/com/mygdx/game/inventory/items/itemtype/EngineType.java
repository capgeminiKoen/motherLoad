package com.mygdx.game.inventory.items.itemtype;

import com.mygdx.game.inventory.items.Engine;

public enum EngineType {
    Standard(new Engine(0, "Standard Engine",500, 1000)),
    Twin_Turbo(new Engine(2500, "Twin Turbo Engine", 750,1300));

    public Engine engine;
    EngineType(Engine engine){
        this.engine = engine;
    }
}
