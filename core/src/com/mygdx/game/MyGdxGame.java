package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Utility.Utility;

public class MyGdxGame extends ApplicationAdapter {
	private int gameWidth = 800;
	private int gameHeight = 480;
	private SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	private Character character;
	private Map map;

	
	@Override
	public void create () {
		map = new Map(2, 15, 1);
        map.blocks[0][0].blockType = BlockType.Empty;
        map.blocks[0][1].blockType = BlockType.Empty;
        map.blocks[0][2].blockType = BlockType.Empty;
        map.blocks[0][3].blockType = BlockType.Empty;
		Manager.map = map;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, gameWidth, gameHeight);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.projection);
		img = new Texture("badlogic.jpg");
		character = new Character();
		character.x = 10;
		character.y = 5;
		character.height = 40;
		character.width = 99;
		Manager.screenSize.x = gameWidth;
		Manager.screenSize.y = gameHeight;
		character.texture = new Texture("heli.gif");
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
		img.dispose();
	}
}
