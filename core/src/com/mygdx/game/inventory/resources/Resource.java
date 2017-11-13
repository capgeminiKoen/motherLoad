package com.mygdx.game.inventory.resources;

import com.badlogic.gdx.graphics.Texture;

public enum Resource {
    None(0, "", null, 0, 0, 0),
    Iron(0, "Iron", "iron.png", 100, 5, 1),
    Silver(1, "Silver", "silver.png", 250, 15, 1.5f),
    Gold(2, "Gold", "gold.png", 750, 25, 2f),
    Diamond(3, "Diamond", "diamond.png", 1500, 75, 4f);

    public String name;
    public Texture texture;
    public int value;
    public int firstOccurence;
    public float weight;
    public int id;

    Resource(int id, String name, String texturePath, int value, int firstOccurence, float weight){
        // Null case
        if(texturePath == null) return;

        this.name = name;
        texture = new Texture(texturePath);
        this.value = value;
        this.firstOccurence = firstOccurence;
        this.weight = weight;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
