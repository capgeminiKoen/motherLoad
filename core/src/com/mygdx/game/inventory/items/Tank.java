package com.mygdx.game.inventory.items;

public enum Tank implements UpgradeItem {

    Normal(new Item("Normal Tank", 0), 30),
    Medium(new Item("Medium Tank", 1000), 50),
    Large(new Item("Large Tank", 2500), 70),
    Extreme(new Item("Extreme Tank", 7500), 100);

    private Item item;
    private float size;

    Tank(Item item, float size) {
        this.item = item;
        this.size = size;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String getProperties() {
        return size + "L";
    }

    public float getSize() {
        return size;
    }
}
