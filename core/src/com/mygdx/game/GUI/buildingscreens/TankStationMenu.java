package com.mygdx.game.GUI.buildingscreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Coordinate;
import com.mygdx.game.GUI.Screen;
import com.mygdx.game.Manager;

import javax.xml.soap.Text;

public class TankStationMenu extends Screen {

    Texture pumpTexture;

    // Standard window is 500x400.
    public TankStationMenu() {
        title = "Tank Station";
        pumpTexture = new Texture("pump.png");
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        // Now add text and stuff
        drawTitle(batch);

        // Draw the pump icon
        drawPumpIcon(batch);

        // Draw some info
        drawInfo(batch);
    }

    // Draw a pump on the screen.
    void drawPumpIcon(SpriteBatch batch){
        batch.setProjectionMatrix(Manager.camera.projection);
        // Draw the pump
        batch.draw(pumpTexture, width / 2 - 240, -height / 2, 240, height);

        batch.setProjectionMatrix(Manager.camera.combined);
    }

    void drawInfo(SpriteBatch batch){
        batch.setProjectionMatrix(Manager.camera.projection);

        Vector2 currentPos = new Vector2(-width / 2 + 10, 0);
        float rowDist = 40;

        // Draw some info
        Manager.font.draw(batch, "Hit enter to buy some FUEL (" + Manager.minFuelAmount + " L)",currentPos.x, currentPos.y);
        // Increment position's y
        currentPos.y -= rowDist;

        Manager.font.draw(batch, "Fuel cost: " + Manager.fuelCost + " $/L",currentPos.x, currentPos.y);
        // Increment position's y
        currentPos.y -= rowDist;
        Manager.font.draw(batch, "current Fuel amount: " + String.format("%.1f", Manager.character.getFuelLevel()) + "/" + Manager.character.getMaxFuelLevel(),currentPos.x, currentPos.y);
        batch.setProjectionMatrix(Manager.camera.combined);

    }


}
