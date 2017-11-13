package com.mygdx.game.inventory.items.itemtype;

import com.mygdx.game.inventory.items.Hull;

public enum HullType {
    Standard(new Hull(0, "Standard Hull", 100)),
    Plated(new Hull(2500, "Plated Hull", 130));

    public Hull hull;
    HullType(Hull hull){
        this.hull = hull;
    }
}
