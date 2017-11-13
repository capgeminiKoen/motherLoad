package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.inventory.resources.Resource;
import com.sun.org.apache.regexp.internal.RE;

public class Block extends Rectangle{

    BlockType blockType;
    Resource resource = Resource.None;
    float health;
    float crackLevel = 60, severeCrackLevel = 30;

    public Block(BlockType newBlockType, int x, int y, int blockSize){
        blockType = newBlockType;
        health = 100;
        this.x = x * blockSize;
        this.y = y * blockSize;
        width = blockSize;
        height = blockSize;
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

    public void setResource(Resource resource){
        this.resource = resource;
    }

    public boolean damage(float damage){
        health -= damage;
        if(blockType == BlockType.Normal && health < crackLevel){
            blockType = BlockType.Cracked;
        }
        if(blockType == BlockType.Cracked && health < severeCrackLevel){
            blockType = BlockType.CrackedSevere;
        }
        if(health <= 0 && blockType != BlockType.Empty){
            blockType = BlockType.Empty;
            return true;
        }
        return false;
    }
}
