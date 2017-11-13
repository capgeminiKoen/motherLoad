package com.mygdx.game.inventory.items.itemtype;

import com.mygdx.game.inventory.items.Drill;

public enum DrillType{
    Iron(new Drill(0, "Iron Drill", 1)),
    Steel(new Drill(5000, "Steel Drill", 1.3f)),
    Diamond(new Drill(25000, "Diamond Drill", 1.8f));

    public Drill drill;
    DrillType(Drill drill){
        this.drill = drill;
    }
}
