package com.mygdx.game.inventory.items;

public enum Engine implements UpgradeItem {

    Standard(new Item("Standard Engine", 0),500, 1000),
    Twin_Turbo(new Item("Twin Turbo Engine", 2500), 750,1300);

    private Item item;
    private int accelerationSpeed;
    private int maxSpeed;

    Engine(Item item, int accelerationSpeed, int maxSpeed) {
        this.item = item;
        this.accelerationSpeed = accelerationSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String getProperties() {
        return "Acceleration: " + accelerationSpeed + ", max: " + maxSpeed;
    }

    public int getAccelerationSpeed() {
        return accelerationSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public Item getItem() {
        return item;
    }
}
