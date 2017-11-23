package com.mygdx.game.inventory.items;

public class Item {
    private String name;
    private int price;
    private boolean available = false;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, int price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
