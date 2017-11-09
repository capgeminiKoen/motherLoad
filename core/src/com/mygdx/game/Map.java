package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Map {

    int height, width;
    int blockSize = 64;
    Block[][] blocks;

    public Map(int newHeight, int newWidth){
        blocks = new Block[newHeight][newWidth];
        height = newHeight;
        width = newWidth;
        initializeBlocks();
    }

    private void initializeBlocks(){
        // For now, lets make all blocks normal at the start.
        // TODO
        // In the future, some have to be different types, as well as empty, etc.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                blocks[i][j] = new Block(BlockType.Normal, j, i, blockSize);
            }
        }
    }

    // Retrieves block on the basis of the inGame coordinates
    public Rectangle getBlockByCoords(int x, int y){
        // Block [0,0] is from [0,0] to [blockSize-1, blockSize-1]
        int blockx = x / blockSize;
        int blocky = y / blockSize;
        // Clamp bottom
        if(blockx < 0) blockx = 0;
        if(blocky < 0) blocky = 0;
        // Clamp top
        if(blockx >= width) blockx = width - 1;
        if(blocky >= height) blocky = height - 1;
        // Retrieve Rect
        return Manager.map.blocks[blocky][blockx];
    }

    public void draw(SpriteBatch batch){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                blocks[i][j].draw(batch);
            }
        }
    }

}
