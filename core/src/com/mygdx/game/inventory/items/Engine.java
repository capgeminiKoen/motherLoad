package com.mygdx.game.inventory.items;

public class Engine extends Item {
    public float accelerationSpeed;
    public float maxSpeed;
    public Engine(int price, String name, float speed, float maxSpeed){
        super(price, name);
        this.accelerationSpeed = speed;
        this.maxSpeed = maxSpeed;
    }
}
