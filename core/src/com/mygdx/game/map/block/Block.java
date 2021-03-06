package com.mygdx.game.map.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.inventory.resources.Resource;

public class Block extends Rectangle{

    public BlockType blockType;
    public Resource resource = Resource.None;
    float crackLevel = 40, severeCrackLevel = 70;

    public Block(BlockType newBlockType, int x, int y, int blockSize, Resource resource){
        blockType = newBlockType;
        this.x = x * blockSize;
        this.y = y * blockSize;
        width = blockSize;
        height = blockSize;
        this.resource = resource;
    }

    public boolean isEmpty(){
        // Checks if there is no block
        return blockType == BlockType.Empty;
    }

    public void draw(SpriteBatch batch){
        if(isEmpty()) return;
        batch.draw(blockType.blockTexture, x, y, width, height);
        if(resource != Resource.None){
            batch.draw(resource.texture, x, y, width, height);
        }
    }

    public Vector2 getCenter() {
        return new Vector2(x + width  /2, y + height / 2);
    }

    public void setResource(Resource resource){
        this.resource = resource;
    }

    // Let the block know what the drilled percentage is ([0.0f - 1.0f])
    public boolean drill(float drillPercentage){
        if(blockType == BlockType.Normal && drillPercentage > crackLevel){
            blockType = BlockType.Cracked;
        }
        if(blockType == BlockType.Cracked && drillPercentage > severeCrackLevel){
            blockType = BlockType.CrackedSevere;
        }
        if(drillPercentage >= 100 && blockType != BlockType.Empty){
            blockType = BlockType.Empty;
            return true;
        }
        return false;
    }
}
