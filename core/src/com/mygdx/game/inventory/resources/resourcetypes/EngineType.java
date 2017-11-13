package com.mygdx.game.inventory.resources.resourcetypes;

import com.mygdx.game.inventory.Engine;

public enum EngineType {
    Standard(new Engine(0, "Standard Engine",100)),
    Twin_Turbo(new Engine(2500, "Twin Turbo Engine", 130));

    public Engine engine;

    EngineType(Engine engine){
        this.engine = engine;
    }
}
