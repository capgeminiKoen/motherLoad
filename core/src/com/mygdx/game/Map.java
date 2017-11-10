package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utility.Utility;

public class Map {

    int height, width;
    int blockSize = 64;
    int pixelWidth;
    private int fillFromLevel;
    Block[][] blocks;

    public Map(int newHeight, int newWidth, int fillMapFromLayer) {
        fillFromLevel = fillMapFromLayer;
        blocks = new Block[newHeight][newWidth];
        height = newHeight;
        width = newWidth;
        pixelWidth = width * blockSize;
        initializeBlocks();
    }

    public Coordinate getHorizontalMapBounds(){
        return new Coordinate(0, pixelWidth);
    }

    private void initializeBlocks() {
        // For now, lets make all blocks normal at the start.
        // TODO
        // In the future, some have to be different types, as well as empty, etc.
        for (int i = 0; i < height - fillFromLevel; i++) {
            for (int j = 0; j < width; j++) {
                blocks[i][j] = new Block(BlockType.Normal, j, i, blockSize);
            }
        }
        for(int i = height - fillFromLevel; i < height; i++){
            for (int j = 0; j < width; j++) {
                blocks[i][j] = new Block(BlockType.Empty, j, i, blockSize);
            }
        }
    }

    public Coordinate getBlockIndexByCoords(int x, int y) {
        // Block [0,0] is from [0,0] to [blockSize-1, blockSize-1]
        int blockx = x / blockSize;
        int blocky = y / blockSize;
        // Clamp bottom
        if (blockx < 0) blockx = Utility.NOT_FOUND;
        if (blocky < 0) blocky = Utility.NOT_FOUND;
        // Clamp top
        if (blockx >= width) blockx = Utility.NOT_FOUND;
        if (blocky >= height) blocky = Utility.NOT_FOUND;
        // Retrieve Rect
        return new Coordinate(blockx, blocky);
    }

    public Coordinate getBlockIndexByCoords(Vector2 coords) {
        return getBlockIndexByCoords(Math.round(coords.x), Math.round(coords.y));
    }

    public Block getBlockByIndex(Coordinate coords) {
        return getBlockByIndex(coords.x, coords.y);
    }

    public Block getBlockByIndex(int x, int y) {
        if(x == Utility.NOT_FOUND || y == Utility.NOT_FOUND)
            return null;
        return blocks[y][x];
    }

    // Returns true when there are blocks within the range of [block1 -> block2].
    public boolean containsBlock(Coordinate block1, Coordinate block2) {
        // Loops through the blocks and returns true if one is true.
        int i_start = block1.x, i_end = block2.x;
        int j_start = block1.y, j_end = block2.y;
        // Loop from istart to iend, INCLUSIVE!
        // If we have coords [1,3] and [1,5] we will evaluate the following blocks:
        // [1,3], [1,4] and [1,5]. If any of these contain obstacles, return true.
        for (int i = i_start; i <= i_end; i++) {
            for (int j = j_start; j <= j_end; j++) {
                // If a block is found, return true.
                if (blocks[j][i].blockType != BlockType.Empty)
                    return true;
            }
        }
        return false;
    }

    // Determine if player transitions to new block column
    public boolean isNewBlockColumnHorizontal(float x_now, float x_next) {
        int x_now_int = Math.round(x_now);
        int x_next_int = Math.round(x_next);
        // Get ingame coordinates
        int currentXCoordinate = worldToBlockIndexHorizontal(x_now_int);
        int nextXCoordinate = worldToBlockIndexHorizontal(x_next_int);
        return (currentXCoordinate != nextXCoordinate);
    }

    // Determine if player transitions to new block row
    public boolean isNewBlockColumnVertical(int y_now, int y_next) {
        int y_now_int = Math.round(y_now);
        int y_next_int = Math.round(y_next);
        // Get ingame coordinates
        int currentYCoordinate = worldToBlockIndexVertical(y_now_int);
        int nextYCoordinate = worldToBlockIndexVertical(y_next_int);
        return (currentYCoordinate != nextYCoordinate);
    }

    // Get the block x index based on the world x pos
    public int worldToBlockIndexHorizontal(int xPos_world) {
        // Block [0,0] is from [0,0] to [blockSize-1, blockSize-1]
        int index = xPos_world / blockSize;
        if (index < 0) index = 0;
        if (index >= width) index = width - 1;
        return index;
    }

    // Get the block y index based on the world y pos
    public int worldToBlockIndexVertical(int yPos_world) {
        // NO CAPS ON Y POS!!! :)
        int index = yPos_world / blockSize;
        return index;
    }

    // Retrieves block on the basis of the inGame coordinates
    public Block getBlockByCoords(int x, int y) {
        // Block [0,0] is from [0,0] to [blockSize-1, blockSize-1]
        int blockx = x / blockSize;
        int blocky = y / blockSize;
        // Clamp bottom
        if (blockx < 0) blockx = 0;
        if (blocky < 0) blocky = 0;
        // Clamp top
        if (blockx >= width) blockx = width - 1;
        if (blocky >= height) blocky = height - 1;
        // Retrieve Rect
        return blocks[blocky][blockx];
    }

    public Block getBlockByCoords(Vector2 coords) {
        return getBlockByCoords(Math.round(coords.x), Math.round(coords.y));
    }

    public Block getBlockByCoords(Coordinate coords) {
        return getBlockByIndex(coords.x, coords.y);
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                blocks[i][j].draw(batch);
            }
        }
    }

}
