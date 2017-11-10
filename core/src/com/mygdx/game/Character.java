package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character extends Rectangle{

    private Vector2 currentMovementSpeed = new Vector2(0,0);
    private float maximumMovementSpeed = 1000;
    public float accelerationSpeed = 500;
    public float dampenSpeedAir = 100, dampenSpeedGround = 400;
    public Texture texture;
    private boolean flip = false;
    private boolean grounded = false;


    public void draw(SpriteBatch batch){
        batch.draw(texture, flip ? x + width : x, y, flip ? -width : width, height);
    }

    public Character(){

    }

    public void accelerate(Direction direction){
        // Determine multiplier
        int multiplier = 0;
        switch (direction){
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
        switch (direction){
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

    private void accelerationX(int multiplier){
        // Increase movementSpeed
        currentMovementSpeed.x += multiplier * accelerationSpeed * Gdx.graphics.getDeltaTime();
        if(Math.abs(currentMovementSpeed.x) > maximumMovementSpeed){
            // Clip the currentMovementSpeed
            currentMovementSpeed.x = (currentMovementSpeed.x < 0) ?  -maximumMovementSpeed : maximumMovementSpeed;
        }
    }

    private void accelerationY(int multiplier){
        // Increase movementSpeed
        currentMovementSpeed.y += multiplier * accelerationSpeed * Gdx.graphics.getDeltaTime();
        if(Math.abs(currentMovementSpeed.y) > maximumMovementSpeed){
            // Clip the currentMovementSpeed
            currentMovementSpeed.y = (currentMovementSpeed.y < 0) ?  -maximumMovementSpeed : maximumMovementSpeed;
        }
    }

    private void applyGravity(){
        if(!grounded) {
            currentMovementSpeed.y -= Manager.gravity * Gdx.graphics.getDeltaTime();
        }
    }

    // Moves the character until there is a collision
    // Input: distance to travel (already corrected for the time)
    private void moveUntilCollisionHorizontal(float amount){
        if(amount == 0) return;
        if(amount < 0){
            if(Manager.map.isNewBlockColumnHorizontal(x, x + amount)) {

                // TODO: FIND OUT WHAT COLUMN IT IS, AND CHECK FROM TOP -> BOT!!
                // Get column
                Coordinate coords_up = Manager.map.getBlockIndexByCoords(new Vector2(x + amount, y + height));
                Coordinate coords_down = Manager.map.getBlockIndexByCoords(new Vector2(x + amount, y));
                if(Manager.map.containsBlock(coords_down, coords_up)){
                    // Calculate collisionpoint
                    Block block = Manager.map.getBlockByIndex(coords_down);
                    block.blockType = BlockType.Cracked;
                    // Maximum travel distance is until we hit the block, so the very right of it. (+1 buffer)
                    float limit = block.x + block.width + 1;
                    // Set x to the limit (otherwise we'd have passed it)
                    x = limit;
                    // Set current X speed to 0
                    currentMovementSpeed.x = 0;

                }
            }
            else{
                x += amount; // J
            }
        }
        else{
            // RIGHT
            x += currentMovementSpeed.x * Gdx.graphics.getDeltaTime();
        }
    }

    private boolean moveUntillCollision(){
        // If we are moving up, we are only colliding up.
        // If our length > blockSize, we can collide with max length/blockSize + 2 blocks.
        // The transition states are most important. We are hovering on blocks, and have to check the next ones.
        // In case of UP ->
        //  get left upper corner of current position, pass it to the map and get corresponding block.
        //  get right upper corner of current position, pass it to the map and get corresponding block.
        //  check whether there is ANY block in between those two. if so; limit movement till those two.
        moveUntilCollisionHorizontal(currentMovementSpeed.x * Gdx.graphics.getDeltaTime());
        return false;
    }

    public void move(){
        x += currentMovementSpeed.x * Gdx.graphics.getDeltaTime();
        y += currentMovementSpeed.y * Gdx.graphics.getDeltaTime();
        if(y > 0){
            grounded = false;
        }

        if(x + width > Manager.screenSize.x){
            currentMovementSpeed.x = 0;
            x = Manager.screenSize.x - width;
        }
        if(x < 0){
            currentMovementSpeed.x = 0;
            x = 0;
        }
        if(y + height > Manager.screenSize.y){
            currentMovementSpeed.y = 0;
            y = Manager.screenSize.y - height;
        }
        if(y < 0){
            grounded = true;
            currentMovementSpeed.y = 0;
            y = 0;
        }
    }

    public void dampenXMovement(){

        float dampenSpeed = grounded ? dampenSpeedGround : dampenSpeedAir;

        if(currentMovementSpeed.x != 0){
            if(currentMovementSpeed.x< 0){
                currentMovementSpeed.x += dampenSpeed * Gdx.graphics.getDeltaTime();
                if(currentMovementSpeed.x > 0){
                    currentMovementSpeed.x = 0;
                }
            }
            else if(currentMovementSpeed.x > 0){
                currentMovementSpeed.x -= dampenSpeed * Gdx.graphics.getDeltaTime();
                if(currentMovementSpeed.x < 0){
                    currentMovementSpeed.x = 0;
                }

            }
        }
    }


    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelerate(Direction.Left);
            flip = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            accelerate(Direction.Right);
            flip = false;
        }
        else {
            // Dampen X movement!
            dampenXMovement();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            accelerate(Direction.Up);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            accelerate(Direction.Down);
        }
        // Move
        moveUntillCollision();
        // Apply gravity
        applyGravity();
    }


}
