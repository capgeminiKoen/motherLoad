package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GUI.Screen;
import com.mygdx.game.GUI.ScreenType;
import com.mygdx.game.Utility.Coordinate;
import com.mygdx.game.Utility.Manager;
import com.mygdx.game.Utility.Utility;
import com.mygdx.game.character.Character;
import com.mygdx.game.map.Map;

public class MyGdxGame extends ApplicationAdapter {
	private int gameWidth = 1200;
	private int gameHeight = 640;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Character character;
	private Map map;

	
	@Override
	public void create () {
	    // Make screenSize available
		Manager.screenSize.x = gameWidth;
		Manager.screenSize.y = gameHeight;
		// New camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, gameWidth, gameHeight);
		Manager.camera = camera;
	    // Create new map
		map = new Map(100, 40, 2, 100);
		map.initializeBlocks();
		// Add ref to manager
		Manager.map = map;
		// Spritebatch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.projection);
		// Blend for HUD
		batch.enableBlending();
		// Create new character
		character = new Character(new String[] {"heli/frame0.gif", "heli/frame1.gif"}, 0.05f, false);
		// Set character in the manager
		Manager.character = character;

		Manager.explosionAnimation = buildExplosionAnimation("animations/explosion/spritesheet.png", 9,9);
	}

    private Animation<TextureRegion> buildExplosionAnimation(String animationSheet, int FRAME_COLS, int FRAME_ROWS){
        Texture explosionSheet = new Texture(animationSheet);
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(0.025f, explosionFrames);

    }

	@Override
	public void render () {
		// THIS IS THE MAIN GAME LOOP!

		// Run update cycle BEFORE setting the projection matrix!!
		update();

		// Set projection matrix for the spriteBatch
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        map.update();
		character.update();
		updateCamera();
	}

	private void updateCamera(){
		// Update camera
		// Follow player -> bound by mapsize
		Coordinate mapBounds = map.getHorizontalMapBounds();
		float cameraX = Utility.clamp(character.getCenter().x,
				mapBounds.x + camera.viewportWidth / 2,
				mapBounds.y - camera.viewportWidth / 2);
		float cameraY = character.getCenter().y;
		camera.position.set(cameraX, cameraY, 0);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Manager.screenSize.x = width;
		Manager.screenSize.y = height;
		camera.setToOrtho(false, width, height);
		// Reset all rects of menus.
		for(ScreenType screenType : ScreenType.values()){
            Screen screen = screenType.getScreen();
            if(screen != null) {
                screen.redrawRect();
            }
        }
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
