package com.mygdx.game.inventory.items;

public enum Trunk implements UpgradeItem {
    Standard(new Item("Standard trunk", 0), 25),
    Medium(new Item("Medium trunk", 2500), 75),
    Large(new Item("Large trunk", 7500), 125),
    Extreme(new Item("Extreme trunk", 25000), 250);

    private Item item;
    private int space;

    Trunk(Item item, int space) {
        this.item = item;
        this.space = space;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String getProperties() {
        return "capacity: " + space;
    }

    public int getSpace() {
        return space;
    }
}
