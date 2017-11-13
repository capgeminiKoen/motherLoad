package com.mygdx.game.inventory.resources.resourcetypes;

import com.mygdx.game.inventory.Hull;

public enum HullType {
    Standard(new Hull(0, "Standard Hull", 100)),
    Plated(new Hull(2500, "Plated Hull", 130));

    public Hull hull;
    HullType(Hull hull){
        this.hull = hull;
    }
}
