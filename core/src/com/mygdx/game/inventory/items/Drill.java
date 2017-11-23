package com.mygdx.game.inventory.items;

public enum Drill implements UpgradeItem {

    Iron(new Item("Iron Drill", 0, true), 1.0f),
    Steel(new Item("Steel Drill", 5000), 1.3f),
    Diamond(new Item("Diamond Drill", 25000), 1.8f);

    private Item item;
    private float drillingSpeed;

    Drill(Item item, float drillingSpeed) {
        this.item = item;
        this.drillingSpeed = drillingSpeed;
    }

    @Override
    public String getProperties() {
        return "Drilling speed: " + drillingSpeed;
    }

    public float getDrillingSpeed(){
        return drillingSpeed;
    }

    @Override
    public Item getItem() {
        return item;
    }
}
