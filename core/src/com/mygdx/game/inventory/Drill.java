package com.mygdx.game.inventory;

public class Drill extends Item {
    public float drillingSpeed;

    public Drill(int price, String name, float drillingSpeed){
        super(price, name);
        this.drillingSpeed = drillingSpeed;
    }
}
