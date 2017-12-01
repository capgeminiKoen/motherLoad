package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.block.Block;
import com.mygdx.game.map.block.BlockType;
import com.mygdx.game.Utility.Coordinate;
import com.mygdx.game.GUI.background.*;
import com.mygdx.game.Utility.Manager;
import com.mygdx.game.Utility.Utility;
import com.mygdx.game.buildings.BuildingType;
import com.mygdx.game.inventory.resources.Resource;

public class Map {

    int height, width;
    int blockSize;
    public int pixelWidth, pixelHeight;
    private int fillFromLevel;
    private float emptyPercentage = 5.0f;
    private Background background;
    Block[][] blocks;

    public Map(int newHeight, int newWidth, int fillMapFromLayer, int blockSize) {
        this.blockSize = blockSize;
        fillFromLevel = fillMapFromLayer;
        blocks = new Block[newHeight][newWidth];
        height = newHeight;
        width = newWidth;
        pixelWidth = width * blockSize;
        pixelHeight = height * blockSize;
        // Make nice-ass sky
        Sky sky = new Sky(new BackgroundGradient[]{
                new BackgroundGradient(0, 500, new Color(0xf8b83aff), new Color(0x933835ff)),
                new BackgroundGradient(500, 1000, new Color(0x933835ff), new Color(0x2a2affff)),
                new BackgroundGradient(1000, 2000, new Color(0x2a2affff), new Color(0x000000ff))
        });
        UndergroundBackground undergroundBackground = new UndergroundBackground(new BackgroundGradient[]{
                new BackgroundGradient(-10000, -5000, new Color(0x2b0000ff), new Color(0x550000ff)),
                new BackgroundGradient(-5000, -2000, new Color(0x550000ff), new Color(0x28170bff)),
                new BackgroundGradient(-2000, -250, new Color(0x28170bff), new Color(0x502d16ff)),
                new BackgroundGradient(-250, 0, new Color(0x502d16ff), new Color(0xf8b83aff))
        });
        background = new Background(new BackgroundSet[] {sky, undergroundBackground});

    }

    public Coordinate getHorizontalMapBounds(){
        return new Coordinate(0, pixelWidth);
    }
    public Coordinate getVerticalMapBounds(){
        return new Coordinate(0,pixelHeight);
    }

    private Resource getRandomResource(int level){
        for (Resource resource : Resource.values()) {
            if(resource.firstOccurrence <= level){
                // If we are lucky
                if(Manager.random.nextFloat() * 100 < resource.occurrencePercentage){
                    return resource;
                }
            }
        }
        return Resource.None;
    }

    // Determines if next
    private boolean isBlockEmpty(){
        return Manager.random.nextFloat() * 100 < emptyPercentage;
    }

    /**
     * Initializes all blocks; starting from fillFromLevel.
     */
    public void initializeBlocks() {
        for (int i = 0; i < height - fillFromLevel; i++) {
            for (int j = 0; j < width; j++) {
                if(i == height - fillFromLevel - 1){
                    blocks[i][j] = new Block(BlockType.Normal, j, i, blockSize, Resource.None);
                }
                else {
                    // Some of the blocks are empty
                    if (isBlockEmpty()) {
                        blocks[i][j] = new Block(BlockType.Empty, j, i, blockSize, Resource.None);
                    } else {
                        blocks[i][j] = new Block(BlockType.Normal, j, i, blockSize, getRandomResource(height - i));
                    }
                }
            }
        }
        // Fill uppermost layers with normal blocks
        for(int i = height - fillFromLevel; i < height; i++){
            for (int j = 0; j < width; j++) {
                blocks[i][j] = new Block(BlockType.Normal, j, i, blockSize, Resource.None);
            }
        }

        // Fill first (so actually last internally) layer with un-destroyable blocks under the buildings.
        // Get the block through those handy functions
        for(BuildingType buildingType : BuildingType.values()){
            int block_x = worldToBlockIndexHorizontal(buildingType.building.position.x);
            int block_x_2 = worldToBlockIndexHorizontal(buildingType.building.position.x + buildingType.building.texture.getWidth());
            // only do something if the building is not outside of the map
            if(block_x == -1 || block_x_2 == -1) {
                System.out.println("Building is out of bounds..");
            }
            else{
                // Fill underground with unbreakable blocks.
                for (int i = block_x; i <= block_x_2; i++) {
                    blocks[height - 1][i].blockType = BlockType.Unbreakable;
                }
            }
        }
    }

    public Block worldCoordinatesToBlock(float x, float y){
        int block_x = worldToBlockIndexHorizontal(Math.round(x));
        int block_y = worldToBlockIndexVertical(Math.round(y));
        return getBlockByIndex(block_x, block_y);
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
        // fix for the NOT_FOUND index
        if((i_start == Utility.NOT_FOUND && i_end == Utility.NOT_FOUND) || (j_start == Utility.NOT_FOUND && j_end == Utility.NOT_FOUND))
            return false;

        // left/lower bounds
        if(i_start == Utility.NOT_FOUND)
            i_start = 0;
        if(j_start == Utility.NOT_FOUND)
            j_start = 0;

        // right/upper bounds
        if(i_end == Utility.NOT_FOUND)
            i_end = height-1;
        if(j_end == Utility.NOT_FOUND)
            j_end = height-1;

        // Loop from istart to iend, INCLUSIVE!
        // If we have coords [1,3] and [1,5] we will evaluate the following blocks:
        // [1,3], [1,4] and [1,5]. If any of these contain obstacles, return true.
        for (int i = i_start; i <= i_end; i++) {
            for (int j = j_start; j <= j_end; j++) {
                // If a block is found, return true.
                if (j != -1 && i != -1 && blocks[j][i].blockType != BlockType.Empty)
                    return true;
            }
        }
        return false;
    }
    // Determine if player transitions to new block column

    public boolean isNewBlockColumnHorizontal(float x_now, float x_next) {
        int x_now_int = Math.round((x_now < x_next) ? x_now : x_next);
        int x_next_int = Math.round((x_next > x_now) ? x_next : x_now);
        // Get ingame coordinates
        int currentXCoordinate = worldToBlockIndexHorizontal(x_now_int);
        int nextXCoordinate = worldToBlockIndexHorizontal(x_next_int);
        return (currentXCoordinate != nextXCoordinate);
    }
    // Determine if player transitions to new block row

    public boolean isNewBlockColumnVertical(float y_now, float y_next) {
        int y_now_int = Math.round((y_now < y_next) ? y_now : y_next);
        int y_next_int = Math.round((y_next > y_now) ? y_next : y_now);
        // Get ingame coordinates
        int currentYCoordinate = worldToBlockIndexVertical(y_now_int);
        int nextYCoordinate = worldToBlockIndexVertical(y_next_int);
        return (currentYCoordinate != nextYCoordinate);
    }
    // Get the block x index based on the world x pos

    public int worldToBlockIndexHorizontal(int xPos_world) {
        // Block [0,0] is from [0,0] to [blockSize-1, blockSize-1]
        int index = xPos_world / blockSize;
        if (index < 0 || index >= width) index = Utility.NOT_FOUND;
        return index;
    }
    // Get the block y index based on the world y pos

    public int worldToBlockIndexVertical(int yPos_world) {
        // NO CAPS ON Y POS!!! :)
        int index = yPos_world / blockSize;
        if(index < 0 || index >= height) index = Utility.NOT_FOUND;
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
        // Start by drawing the sky
        background.draw(batch);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                blocks[i][j].draw(batch);
            }
        }
        drawBuildings(batch);

    }

    private void drawBuildings(SpriteBatch batch){
        for(BuildingType buildingType : BuildingType.values()){
            // Draw each building
            buildingType.building.draw(batch);
        }
    }

    // Cascades
    public void update(){
        background.update();
    }
}
