package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle{

    BlockType blockType;
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

    public void draw(SpriteBatch batch){
        batch.draw(blockType.blockTexture, x, y, width, height);
    }

    public void damage(float damage){
        health -= damage;
        if(blockType == BlockType.Normal && health < crackLevel){
            blockType = BlockType.Cracked;
        }
        if(blockType == BlockType.Cracked && health < severeCrackLevel){
            blockType = BlockType.CrackedSevere;
        }
    }
}
