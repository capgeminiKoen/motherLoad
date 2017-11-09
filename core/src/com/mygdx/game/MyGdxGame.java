package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
		camera = new OrthographicCamera();
		camera.setToOrtho(false, gameWidth, gameHeight);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		character = new Character();
		character.x = gameWidth/2 - 64/2;
		character.y = 300;
		character.height = 40;
		character.width = 99;
		Manager.screenSize.x = gameWidth;
		Manager.screenSize.y = gameHeight;
		character.texture = new Texture("heli.gif");
		map = new Map(4, 7);
	}

	@Override
	public void render () {
		// THIS IS THE MAIN GAME LOOP!

		// Update camera
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
