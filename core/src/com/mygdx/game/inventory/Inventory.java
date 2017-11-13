package com.mygdx.game.inventory;

import com.mygdx.game.inventory.items.Engine;
import com.mygdx.game.inventory.items.Hull;
import com.mygdx.game.inventory.resources.Resource;
import com.mygdx.game.inventory.items.itemtype.DrillType;

public class Inventory {
    private DrillType drillType;
    private Engine engine;
    private Hull hull;
    private
    int[] resources;

    public Inventory(){
        resources = new int[Resource.values().length - 1]; // -1 for None
        for (int i = 0; i < resources.length; i++) {
            resources[i] = 0;
        }
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
