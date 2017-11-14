package com.mygdx.game.inventory;

import com.mygdx.game.inventory.items.Engine;
import com.mygdx.game.inventory.items.Hull;
import com.mygdx.game.inventory.items.itemtype.EngineType;
import com.mygdx.game.inventory.items.itemtype.HullType;
import com.mygdx.game.inventory.items.itemtype.TrunkType;
import com.mygdx.game.inventory.resources.Resource;
import com.mygdx.game.inventory.items.itemtype.DrillType;

public class Inventory {

    // Get standard Items
    private DrillType drillType = DrillType.Iron;
    private EngineType engineType = EngineType.Standard;
    private HullType hullType = HullType.Standard;
    private TrunkType trunkType = TrunkType.Standard;

    int[] resources;

    public Inventory(){
        resources = new int[Resource.values().length - 1]; // -1 for None
        for (int i = 0; i < resources.length; i++) {
            resources[i] = 0;
        }
    }

    public float getDrillSpeed(){
        return drillType.drill.drillingSpeed;
    }

    public float getHullStrength(){
        return hullType.hull.protection;
    }

    public float getAccelerationSpeed(){
        return engineType.engine.accelerationSpeed;
    }

    public float getMaxSpeed(){
        return engineType.engine.maxSpeed;
    }
    // Add a resource
    public void addResource(Resource resource){
        resources[resource.id]++;
    }

    // Sell resources and return the yield
    public int sellResource(Resource resource, boolean all){
        int yield = 0;
        if(all){
            yield = resources[resource.id] * resource.value;
            resources[resource.id] = 0;
        }
        else{
            yield = resource.value;
            resources[resource.id]--;
        }
        return yield;
    }
}
