package com.mygdx.game.inventory.items;

public class Engine extends Item {
    public float speed;
    public Engine(int price, String name, float speed){
        super(price, name);
        this.speed = speed;
    }
}
