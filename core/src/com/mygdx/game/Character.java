package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GUI.Hud;
import com.mygdx.game.GUI.InventoryScreen;
import com.mygdx.game.GUI.ScreenType;
import com.mygdx.game.Utility.Utility;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.resources.Resource;

public class Character extends Rectangle {

    private Vector2 currentMovementSpeed = new Vector2(0, 0);
    private Vector2 drillingStartPosition;
    private float dampenSpeedAir = 100, dampenSpeedGround = 400;
    private float currentDrillingTime = 0.0f;
    private Texture texture;
    private boolean flip = false;
    private boolean grounded = false;
    private boolean isDrilling = false;
    private Block blockToDrill;
    private Inventory inventory;
    private Hud hud;
    private int health = 100, maxHealth = 100;
    private int money = 0;


    public void draw(SpriteBatch batch) {
        batch.draw(texture, flip ? x + width : x, y, flip ? -width : width, height);
        hud.draw(batch);
    }

    public Character(String texturePath) {
         texture = new Texture(texturePath);
         inventory = new Inventory();
         hud = new Hud(250,25);
         // Set inventory of character
        ((InventoryScreen) ScreenType.Inventory.getScreen()).setInventory(inventory);
    }

    private void accelerate(Direction direction) {
        // Determine multiplier
        int multiplier = 0;
        switch (direction) {
            case Down:
            case Left:
                multiplier = -1;
                break;
            case Right:
            case Up:
                multiplier = 1;
                break;
        }
        // Execute the acceleration
        switch (direction) {
            case Down:
            case Up:
                accelerationY(multiplier);
                break;
            case Right:
            case Left:
                accelerationX(multiplier);
                break;
        }
    }

    private void accelerationX(int multiplier) {
        // Increase movementSpeed
        currentMovementSpeed.x += multiplier * inventory.getAccelerationSpeed() * Gdx.graphics.getDeltaTime();
        if (Math.abs(currentMovementSpeed.x) > inventory.getMaxSpeed()) {
            // Clip the currentMovementSpeed
            currentMovementSpeed.x = (currentMovementSpeed.x < 0) ? -inventory.getMaxSpeed() : inventory.getMaxSpeed();
        }
    }

    private void accelerationY(int multiplier) {
        // Increase movementSpeed
        currentMovementSpeed.y += multiplier * inventory.getAccelerationSpeed() * Gdx.graphics.getDeltaTime();
        if (Math.abs(currentMovementSpeed.y) > inventory.getMaxSpeed()) {
            // Clip the currentMovementSpeed
            currentMovementSpeed.y = (currentMovementSpeed.y < 0) ? -inventory.getMaxSpeed() : inventory.getMaxSpeed();
        }
    }

    private void applyGravity() {
        if (!grounded) {
            currentMovementSpeed.y -= Manager.gravity * Gdx.graphics.getDeltaTime();
        }
    }

    // Moves the character until there is a collision
    // Input: distance to travel (already corrected for the time)
    private void moveUntilCollisionHorizontal(float amount) {
        if (amount == 0) return;

        // Check map bounds left and right
        float nextPos = x + amount;
        if(nextPos < 0){
            // Clip to the left of the map
            x = 0;
            currentMovementSpeed.x = 0;
            return;
        }
        else if(nextPos + width > Manager.map.pixelWidth){
            x = Manager.map.pixelWidth - width;
            currentMovementSpeed.x = 0;
        }

        // Check for collision with blocks
        boolean movingLeft = amount < 0;
        // Note: Calculate the reference point for collision. moving right > right, moving left > left.
        float reference_x = (movingLeft) ? x : x + width;
        if (Manager.map.isNewBlockColumnHorizontal(reference_x, reference_x + amount)) {
            // Get column
            Coordinate coords_up = Manager.map.getBlockIndexByCoords(new Vector2(reference_x + amount, y + height));
            Coordinate coords_down = Manager.map.getBlockIndexByCoords(new Vector2(reference_x + amount, y));
            // check if a collision is found.
            if (Manager.map.containsBlock(coords_down, coords_up)) {
                // Calculate collisionpoint
                Block block = Manager.map.getBlockByIndex(coords_down);
                if(block == null) return;
                // When we found a block and we are grounded, drill it :)
                if(grounded){
                    // Drill block
                    drill(block);
                    currentMovementSpeed.y = 0;
                    return;
                }

                // Maximum travel distance is until we hit the block, so the very edge of it. (+1 buffer)
                float limit;
                if(movingLeft)
                    limit = block.x + block.width + 1;
                else
                    limit = block.x - 1 - width;

                // Set x to the limit (otherwise we'd have passed it)
                x = limit;
                // Set current X speed to 0
                currentMovementSpeed.x = 0;
            } else {
                x += amount; // MOVE
            }
        } else {
            x += amount; // J
        }
    }

    // Start drilling a block (:
    private void drill(Block block){
        blockToDrill = block;
        isDrilling = true;
        drillingStartPosition = getCenter();
    }

    private void drillDown(){
        // Get correct block below center of sprite
        // Subtract 5 pixels to be sure to get the right block.
        Block block = Manager.map.worldCoordinatesToBlock(x + width / 2, y - 5);
        if(block != null && block.blockType != BlockType.Empty){
            drill(block);
        }
    }

    // Moves until we hit something!
    private void moveUntilCollisionVertical(float amount){
        if(amount == 0){
            return;
        }

        // Check for collision with blocks
        boolean movingDown = amount < 0;

        // Update grounded
        if(!movingDown){
            grounded = false;
        }
        // If already grounded, do nothing if we pull down. Maybe drill, that's it
        else if(grounded){
            drillDown();
            currentMovementSpeed.y = 0; // Otherwise it will increase all the time.
            return;
        }
        // Note: Calculate the reference point for collision. moving right > right, moving left > left.
        float reference_y = (movingDown) ? y : y + height;
        if (Manager.map.isNewBlockColumnVertical(reference_y, reference_y + amount)) {
            // Get column
            Coordinate coords_left = Manager.map.getBlockIndexByCoords(new Vector2(x, reference_y + amount));
            Coordinate coords_right = Manager.map.getBlockIndexByCoords(new Vector2(x + width, reference_y + amount));
            // check if a collision is found.
            if (Manager.map.containsBlock(coords_left, coords_right)) {
                // Calculate collisionpoint
                Block block = Manager.map.getBlockByIndex(coords_left);

                // Hit block! if speed > Manager.thresholdspeed do damage
                if(movingDown && currentMovementSpeed.y < -Manager.fallDamageSpeedThreshold){
                    doDamage(-currentMovementSpeed.y);
                }

                // Maximum travel distance is until we hit the block, so the very edge of it. (+1 buffer)
                float limit;
                if(movingDown) {
                    limit = block.y + block.height;
                    grounded = true;
                }
                else {
                    limit = block.y - 1 - height;
                }

                // Set x to the limit (otherwise we'd have passed it)
                y = limit;
                // Set current X speed to 0
                currentMovementSpeed.y = 0;
            } else {
                y += amount; // MOVE
            }
        } else {
            y += amount; // J
        }
    }

    private void doDamage(float amount){
        // Calculate amount using hull strength
        amount = (amount * 5) / inventory.getHullStrength();
        health -= amount;
        if(health <= 0){
            health = 0;
            die();
        }

        // Play hit animation
        hud.showHitEffect();
    }

    private void die(){
        // TODO
        // Do something
    }

    private boolean moveUntillCollision() {
        // If we are moving up, we are only colliding up.
        // If our length > blockSize, we can collide with max length/blockSize + 2 blocks.
        // The transition states are most important. We are hovering on blocks, and have to check the next ones.
        // In case of UP ->
        //  get left upper corner of current position, pass it to the map and get corresponding block.
        //  get right upper corner of current position, pass it to the map and get corresponding block.
        //  check whether there is ANY block in between those two. if so; limit movement till those two.
        moveUntilCollisionHorizontal(currentMovementSpeed.x * Gdx.graphics.getDeltaTime());
        moveUntilCollisionVertical(currentMovementSpeed.y * Gdx.graphics.getDeltaTime());
        return false;
    }

    public void dampenXMovement() {

        float dampenSpeed = grounded ? dampenSpeedGround : dampenSpeedAir;

        if (currentMovementSpeed.x != 0) {
            if (currentMovementSpeed.x < 0) {
                currentMovementSpeed.x += dampenSpeed * Gdx.graphics.getDeltaTime();
                if (currentMovementSpeed.x > 0) {
                    currentMovementSpeed.x = 0;
                }
            } else if (currentMovementSpeed.x > 0) {
                currentMovementSpeed.x -= dampenSpeed * Gdx.graphics.getDeltaTime();
                if (currentMovementSpeed.x < 0) {
                    currentMovementSpeed.x = 0;
                }

            }
        }
    }

    // Drilltime update
    private void updateDrillTime(){
        currentDrillingTime += (inventory.getDrillSpeed() / blockToDrill.resource.baseDrillTime) *
                Gdx.graphics.getDeltaTime();
    }

    private void updateDrillingMovement(float percentage){
        float delta_x = blockToDrill.getCenter().x - drillingStartPosition.x;
        x = drillingStartPosition.x - width / 2 + delta_x * percentage;
        float delta_y = blockToDrill.getCenter().y - drillingStartPosition.y;
        y = drillingStartPosition.y - height / 2 + delta_y * percentage;
    }

    private Vector2 getCenter(){
        return new Vector2(x + width / 2, y + height / 2);
    }

    private void moveTowardsDrillTarget(){
        // Update the drilling time
        updateDrillTime();
        // Update position
        updateDrillingMovement(currentDrillingTime);
        // Make a percentage
        if(blockToDrill.drill(currentDrillingTime * 100)) {
            destroyBlock(blockToDrill);
        }
    }

    private void destroyBlock(Block block){
        currentDrillingTime = 0.0f;
        isDrilling = false;
        // If block is broken, set done
        if (block.resource != Resource.None) {
            // If block contains a resource, add one to the inventory
            inventory.addResource(block.resource);
        }
        // Ground dissapeared ;-)
        grounded = false;
        // Set speed to 0
        currentMovementSpeed.y = 0;
        currentMovementSpeed.x = 0;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            accelerate(Direction.Left);
            flip = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            accelerate(Direction.Right);
            flip = false;
        } else {
            // Dampen X movement!
            dampenXMovement();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerate(Direction.Up);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            accelerate(Direction.Down);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            hud.switchMenu(ScreenType.Inventory);
        }

        if(!isDrilling) {
            // Move
            moveUntillCollision();
        }
        else{
            // Drill to position
            moveTowardsDrillTarget();
        }
        // Apply gravity
        applyGravity();
    }

    public int getMoney() {
        return money;
    }

    public float getHealthPercentage(){
        return health / maxHealth;
    }
}
