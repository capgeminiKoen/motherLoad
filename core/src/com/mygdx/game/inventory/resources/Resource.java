package com.mygdx.game.inventory.resources;

import com.badlogic.gdx.graphics.Texture;

public enum Resource {
    None(0, "", null, 0, 0, 0, 0, 0.3f),
    Iron(0, "Iron", "iron.png", 100, 5, 6, 1, 0.5f),
    Silver(1, "Silver", "silver.png", 250, 15, 4, 1.5f, 0.7f),
    Gold(2, "Gold", "gold.png", 750, 25, 2, 2f, 1.0f),
    Diamond(3, "Diamond", "diamond.png", 1500, 75, 1, 4f, 1.5f);

    public String name;
    public Texture texture;
    public int value;
    public int firstOccurrence;
    public float occurrencePercentage;
    public float weight;
    public float baseDrillTime;
    public int id;

    Resource(int id, String name, String texturePath, int value, int firstOccurence, float percentageOccurence, float weight, float drillTime){

        this.occurrencePercentage = percentageOccurence;
        this.value = value;
        this.firstOccurrence = firstOccurence;
        this.weight = weight;
        this.id = id;
        this.baseDrillTime = drillTime;
        this.name = name;

        // Null case
        if(texturePath == null) {
            return;
        }
        else {
            texture = new Texture(texturePath);
        }

    }

    @Override
    public String toString() {
        return name;
    }
}
