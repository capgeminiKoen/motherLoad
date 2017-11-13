package com.mygdx.game.inventory;

public class Hull extends Item {
    public float protection;

    public Hull(int price, String name, float protection){
        super(price, name);
        this.protection = protection;
    }
}
