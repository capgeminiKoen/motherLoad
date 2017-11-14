package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Utility.Utility;
import com.mygdx.game.inventory.resources.Resource;

public class MyGdxGame extends ApplicationAdapter {
	private int gameWidth = 800;
	private int gameHeight = 480;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Character character;
	private Map map;

	
	@Override
	public void create () {
	    // Create new map
		map = new Map(10, 15, 1, 100);
		map.initializeBlocks();
		// Add ref to manager
		Manager.map = map;
		// New camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, gameWidth, gameHeight);
		// Spritebatch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.projection);
		// Blend for HUD
		batch.enableBlending();
		// Create new character
		character = new Character("heli.gif");
		// Get map bounds;
        Coordinate map_hor = map.getHorizontalMapBounds(), map_vert = map.getVerticalMapBounds();
		character.y = map_vert.y;
		character.x = map_hor.y / 2;
		character.height = 40;
		character.width = 99;
		// Save screensize
		Manager.screenSize.x = gameWidth;
		Manager.screenSize.y = gameHeight;
	}

	@Override
	public void render () {
		// THIS IS THE MAIN GAME LOOP!

		// Set projection matrix for the spriteBatch
		batch.setProjectionMatrix(camera.combined);

		// Update camera
		// Follow player -> bound by mapsize
		Coordinate mapBounds = map.getHorizontalMapBounds();
		float cameraX = Utility.clamp(character.getX(),
				mapBounds.x + camera.viewportWidth / 2,
				mapBounds.y - camera.viewportWidth / 2);
		float cameraY = character.getY();
		camera.position.set(cameraX, cameraY, 0);
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Run update cycle
		update();

		// Begin drawing the batch
		batch.begin();
		// Draw map
		map.draw(batch);
		// Draw character
		character.draw(batch);
		batch.end();
	}

	public void update(){
		// Update the character
		character.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
