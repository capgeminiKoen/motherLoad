package com.mygdx.game.inventory;

import com.mygdx.game.GUI.ScreenType;
import com.mygdx.game.GUI.menus.WareHouseMenu;
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
        // Set the inventory to the warehouseMenu.
        ((WareHouseMenu)ScreenType.WarehouseMenu.getScreen()).setResourceAmount(resources);
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
    // Only add when we have space left!
    public void addResource(Resource resource){
        if(currentWeight + resource.weight > trunkType.getSpace()){
            return;
        }
        else {
            resources[resource.id]++;
            currentWeight += resource.weight;
            currentInventoryValue += resource.value;
        }
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

    public int sellResources(){
        int total = 0;
        for(Resource resource : Resource.values()){
            total += sellResource(resource, true);
        }
        return total;
    }

    // Get the current inventory value
    public int getCurrentInventoryValue() {
        return currentInventoryValue;
    }

    public void setDrillType(Drill drillType) {
        this.drillType = drillType;
    }

    public void setEngineType(Engine engineType) {
        this.engineType = engineType;
    }

    public void setHullType(Hull hullType) {
        this.hullType = hullType;
    }

    public void setTrunkType(Trunk trunkType) {
        this.trunkType = trunkType;
    }

    public void setTankType(Tank tankType) {
        this.tankType = tankType;
    }

    public void selectItem(UpgradeItem itemToBuy){
        // If we arrive here, we either already owned the item or have just bought it ;-)
        // TODO: Cleaner way of doing this
        // For now, we use a nice-ass switch case to figure out the type of the item.
        if(itemToBuy instanceof Tank){
            tankType = (Tank)itemToBuy;
        }
        else if(itemToBuy instanceof Hull){
            hullType = (Hull)itemToBuy;
        }
        else if(itemToBuy instanceof Engine){
            engineType = (Engine)itemToBuy;
        }
        else if(itemToBuy instanceof  Trunk){
            trunkType = (Trunk)itemToBuy;
        }
        else if(itemToBuy instanceof Drill){
            drillType = (Drill)itemToBuy;
        }
        // END Ugly switch
    }
}
