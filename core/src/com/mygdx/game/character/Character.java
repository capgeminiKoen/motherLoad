package com.mygdx.game.character;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.GUI.Hud;
import com.mygdx.game.GUI.InventoryScreen;
import com.mygdx.game.GUI.ScreenType;
import com.mygdx.game.GUI.buildingscreens.ItemScreen;
import com.mygdx.game.GUI.buildingscreens.TankStationMenu;
import com.mygdx.game.GUI.buildingscreens.WareHouseMenu;
import com.mygdx.game.Utility.Utility;
import com.mygdx.game.buildings.Building;
import com.mygdx.game.buildings.BuildingType;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.items.Item;
import com.mygdx.game.inventory.items.UpgradeItem;
import com.mygdx.game.inventory.resources.Resource;

import java.security.Key;

public class Character extends Rectangle {

    private float gameTime = 0.0f;
    private Vector2 currentMovementSpeed = new Vector2(0, 0);
    private Vector2 drillingStartPosition;
    private float dampenSpeedAir = 100, dampenSpeedGround = 400;
    private float currentDrillingTime = 0.0f;
    private Animation<Texture> characterAnimation;
    private boolean textureFacingRight = false;
    private boolean flip = false;
    private boolean grounded = false;
    private boolean isDrilling = false;
    private boolean dead = false;
    private Building currentBuilding = null;
    private Block blockToDrill;
    private Inventory inventory;
    private Hud hud;
    private float health = 10, maxHealth = 100;
    private int money = 500;
    private float fuelLevel;
    private float fuelDeclineRate = 0.5f; // per sec


    public void draw(SpriteBatch batch) {
        if(dead){
            TextureRegion textureRegion = Manager.explosionAnimation.getKeyFrame(gameTime);
            batch.draw(textureRegion, x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        }
        else {
            batch.draw(characterAnimation.getKeyFrame(gameTime), flip ? x + width : x, y, flip ? -width : width, height);
        }
        hud.draw(batch);
    }

    private Character(){
        inventory = new Inventory();
        // Initialize fuelLevel to its maximum.
        fuelLevel = inventory.getTankSize();
        hud = new Hud();
        // Set inventory of character
        ((InventoryScreen) ScreenType.Inventory.getScreen()).setInventory(inventory);
    }

    public Character(String[] textures, float animationSpeed, boolean left){
        this();
        textureFacingRight = !left;
        // Fill texture list
        Texture[] textureList = new Texture[textures.length];
        for (int i = 0; i < textures.length; i++) {
            textureList[i] = new Texture(textures[i]);
        }
        // Make new animation
        characterAnimation = new Animation<Texture>(animationSpeed, textureList);
        // Set mode to loop :D
        characterAnimation.setPlayMode(Animation.PlayMode.LOOP);

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

    // Apply gravity if we are not grounded.
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

    // Update fuel levels
    private void reduceFuel(){
        fuelLevel -= fuelDeclineRate * Gdx.graphics.getDeltaTime();
        if(fuelLevel < 0){
            fuelLevel = 0;
            die();
        }
    }

    // Start drilling a block (:
    private void drill(Block block){
        if(!block.blockType.isDrillable()){
            return;
        }
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

    // Check all buildings if we are currently overlapping them
    private void checkBuildingOverlap(){
        // If we are still colliding with a building, only check that one and report when we leave.
        if(currentBuilding != null){
            if(!currentBuilding.containsPosition(getCenter())){
                currentBuilding = null;
                hud.switchMenu(ScreenType.None);
            }
        }
        // We can only enter a building when we are not showing any menus
        else if(hud.getCurrentScreen() == ScreenType.None) {
            for (BuildingType buildingType : BuildingType.values()) {
                if (buildingType.building.containsPosition(getCenter())) {
                    hud.switchMenu(buildingType.screenType);
                    currentBuilding = buildingType.building;
                }
            }
        }
    }


    private void doDamage(float amount){
        System.out.println("Recieved damage :(");
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
        dead = true;
        // Reset gameTime for the animation!
        gameTime = 0;
    }

    public int getMoney() {
        return money;
    }

    // buy an item if we have the monages
    void buyItem(UpgradeItem itemToBuy) {
        int price = itemToBuy.getItem().getPrice();
        if (!itemToBuy.getItem().isAvailable()) {
            if (price <= money) {
                // Buy item
                money -= price;
                // Let the item know that we bought it, hehe
                itemToBuy.getItem().setAvailable(true);
            } else {
                // NOT ENOUGH MONEYY
                return;
            }
        }

        // Select the item
        inventory.selectItem(itemToBuy);
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


        // Update the gameTime
        gameTime += Gdx.graphics.getDeltaTime();

        if(dead) return;

        // update fuel levels
        reduceFuel();

        // Figure out if we are entering a building
        checkBuildingOverlap();

        // Handle input
        handleInput();

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

    private void handleInput(){

        if(hud.isInMenu()){
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                if(hud.getCurrentScreen().getScreen() instanceof ItemScreen) {
                    ItemScreen itemScreen = (ItemScreen)hud.getCurrentScreen().getScreen();
                    itemScreen.previousItem();
                }
            }
            // If input pressed, go down in menu.
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                if(hud.getCurrentScreen().getScreen() instanceof ItemScreen) {
                    ItemScreen itemScreen = (ItemScreen)hud.getCurrentScreen().getScreen();
                    itemScreen.nextItem();
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                // If we are in an itemscreen
                if(hud.getCurrentScreen().getScreen() instanceof ItemScreen){
                    ItemScreen itemScreen = (ItemScreen)hud.getCurrentScreen().getScreen();
                    UpgradeItem upgradeItem = itemScreen.getCurrentlySelectedItem();
                    buyItem(upgradeItem);
                }
                // Buy fuel if enter is hit
                else if(hud.getCurrentScreen().getScreen() instanceof TankStationMenu){
                    buyFuel();
                }
                else if(hud.getCurrentScreen().getScreen() instanceof WareHouseMenu){
                    WareHouseMenu currentMenu = (WareHouseMenu)hud.getCurrentScreen().getScreen();
                    Resource selectedResource = currentMenu.getSelectedResource();
                    if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)){
                        // Sell all of this resource
                        money += inventory.sellResource(selectedResource, true);
                    }
                    else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        money += inventory.sellResources();
                    }
                    else {
                        // Sell 1 of this resource
                        money += inventory.sellResource(selectedResource, false);
                    }
                }
            }
        }
        else{
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(hud.getCurrentScreen() == ScreenType.None) {
                    accelerate(Direction.Up);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if(hud.getCurrentScreen() == ScreenType.None) {
                    accelerate(Direction.Down);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                accelerate(Direction.Left);
                flip = textureFacingRight;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                accelerate(Direction.Right);
                flip = !textureFacingRight;
            }
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // Dampen X movement!
            dampenXMovement();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            hud.toggleMenu(ScreenType.Inventory);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            hud.switchMenu(ScreenType.None);
        }
    }

    public float getCurrentHeight(){
        return y - Manager.map.pixelHeight;
    }

    private void buyFuel(){
        float litersToBuy;
        if(fuelLevel + Manager.minFuelAmount > getMaxFuelLevel()){
            litersToBuy = getMaxFuelLevel() - fuelLevel;
        }
        else{
            litersToBuy = Manager.minFuelAmount;
        }

        // Calculate total cost.
        float totalCost = litersToBuy * Manager.fuelCost;
        // Buy if we have enough monages.
        if(money >= totalCost){
            money -= totalCost;
            fuelLevel += litersToBuy;
        }
        else{
            // We do not have sufficient funds.
            return;
        }
    }

    public float getHealthPercentage(){
        return health / maxHealth;
    }
    public float getFuelPercentage() {
        return fuelLevel / inventory.getTankSize();
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public float getMaxFuelLevel(){
        return inventory.getTankSize();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
