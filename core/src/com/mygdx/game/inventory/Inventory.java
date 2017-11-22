package com.mygdx.game.inventory;

import com.mygdx.game.inventory.items.*;
import com.mygdx.game.inventory.resources.Resource;

public class Inventory {

    // Get standard Items
    private Drill drillType = Drill.Iron;
    private Engine engineType = Engine.Standard;
    private Hull hullType = Hull.Standard;
    private Trunk trunkType = Trunk.Standard;
    private Tank tankType = Tank.Normal;

    private float currentWeight = 0;
    private int currentInventoryValue = 0;

    public int[] getResources() {
        return resources;
    }

    int[] resources;

    public Inventory(){
        resources = new int[Resource.values().length];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = 0;
        }
    }

    public float getDrillSpeed(){
        return drillType.getDrillingSpeed();
    }

    public float getHullStrength(){
        return hullType.getProtection();
    }

    public float getAccelerationSpeed(){
        return engineType.getAccelerationSpeed();
    }

    public float getTankSize(){
        return tankType.getSize();
    }

    // Get total weight of resources currently in trunk.
    public float getResourceWeight(){
        return currentWeight;
    }

    // Get total trunk size.
    public int getTrunkSize(){
        return trunkType.getSpace();
    }

    public float getMaxSpeed(){
        return engineType.getMaxSpeed();
    }

    // Add a resource
    public void addResource(Resource resource){
        resources[resource.id]++;
        currentWeight += resource.weight;
        currentInventoryValue += resource.value;
    }

    // Sell resources and return the yield
    public int sellResource(Resource resource, boolean all){
        int soldResources;
        int yield = 0;
        if(all){
            soldResources = resources[resource.id];
        }
        else{
            soldResources = 1;
        }

        yield = soldResources * resource.value;
        // Update the currentInventoryValue
        currentInventoryValue -= yield;
        currentWeight -= soldResources * resource.weight;
        resources[resource.id] -= soldResources;
        return yield;
    }

    // Get the current inventory value
    public int getCurrentInventoryValue() {
        return currentInventoryValue;
    }
}
