package com.mygdx.game.inventory.items;

public class Trunk extends Item {
    int space;
    public Trunk(int price, String name, int trunkSpace){
        super(price, name);
        this.space = trunkSpace;
    }
}