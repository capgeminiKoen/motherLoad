package com.mygdx.game.inventory.items;

public class Drill extends Item {
    public float drillingSpeed;

    public Drill(int price, String name, float drillingSpeed){
        super(price, name);
        this.drillingSpeed = drillingSpeed;
    }
}
