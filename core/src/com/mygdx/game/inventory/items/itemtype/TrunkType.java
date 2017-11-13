package com.mygdx.game.inventory.items.itemtype;

import com.mygdx.game.inventory.items.Trunk;

public enum TrunkType {
    Standard(new Trunk(0, "Standard trunk", 100)),
    Medium(new Trunk(2500, "Medium trunk", 150));

    Trunk trunk;
    TrunkType(Trunk trunk){
        this.trunk = trunk;
    }
}
