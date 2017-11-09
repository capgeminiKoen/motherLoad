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

    public boolean moveUntillCollision(float amount, Direction direction){
        // If we are moving up, we are only colliding up.
        // If our length > blockSize, we can collide with max length/blockSize + 2 blocks.
        // The transition states are most important. We are hovering on blocks, and have to check the next ones.
        // In case of UP ->
        //  get left upper corner of current position, pass it to the map and get corresponding block.
        //  get right upper corner of current position, pass it to the map and get corresponding block.
        //  check whether there is ANY block in between those two. if so; limit movement till those two.
        switch (direction){
            case Left:
                // Get upper left corner
                Vector2 upperLeftCorner = new Vector2(this.x, this.y + height);
                Vector2 lowerLeftCorner = new Vector2(this.x, this.y + height);
                Manager.map.getBlockByCoords(upperLeftCorner);
        }
//        Rectangle x = Manager.map.getBlockByCoords();
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
        move();
        // Apply gravity
        applyGravity();
    }


}
