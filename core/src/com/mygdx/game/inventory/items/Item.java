package com.mygdx.game.inventory.items;

public abstract class Item {
    int price;
    String name;

    public Item(int price, String name){
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
