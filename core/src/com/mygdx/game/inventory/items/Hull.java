package com.mygdx.game.inventory.items;

public enum Hull implements UpgradeItem {

    Standard(new Item("Standard Hull", 0), 100),
    Plated(new Item("Plated Hull", 2500), 130);

    private Item item;
    private float protection;

    Hull(Item item, float protection) {
        this.item = item;
        this.protection = protection;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String getProperties() {
        return "Protection level " + protection;
    }

    public float getProtection() {
        return protection;
    }
}
