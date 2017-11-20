package com.mygdx.game.inventory.items;

public class Tank extends Item {

    private float size;

    public float getSize() {
        return size;
    }

    public Tank(int price, String name, float tankSize) {
        super(price, name);
        this.size = tankSize;
    }
}
