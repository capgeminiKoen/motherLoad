package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Utility.Manager;

import static com.mygdx.game.Utility.Manager.font;

public abstract class Screen {

    protected int width, height;
    protected int right, left, top, down;
    protected Anchor anchor;
    protected String title;
    protected int topSize = 20;
    protected Rectangle rect;

    public void draw(SpriteBatch batch){
        // Draw background
        drawBackground(batch);
    }

    protected void drawBackground(SpriteBatch batch){
        // Skip spriteBatch right now
        batch.end();

        // Draw the hit
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        // Bar itself
        Color color = new Color(0.4f,0.4f,0.4f,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);

        // Draw rect
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);

        shapeRenderer.end();

        // Start batch again
        batch.begin();
    }

    protected void drawTitle(SpriteBatch batch){

        // Set projection matrix to the camera's position
        batch.setProjectionMatrix(Manager.camera.projection);

        // Draw title
        font.draw(batch, title, rect.x - Manager.screenSize.x / 2, - Manager.screenSize.y / 2 + rect.y + rect.height);

        // Reset projection matrix
        batch.setProjectionMatrix(Manager.camera.combined);
    }

    public Screen(){
        this(500,400,Anchor.CENTER);
    }

    public Screen(int width, int height, Anchor anchor){
        this.anchor = anchor;
        this.width = width;
        this.height = height;

        initializeRect();
    }

    public Screen(int width, int height, Anchor anchor, int left, int right, int top, int down){
        this(width,height,anchor);
        this.left = left;
        this.right = right;
        this.top = top;
        this.down = down;
    }

    public void redrawRect(){
        initializeRect();
    }

    // Initialize rectangle for a menu.
    protected void initializeRect(){
        float right_x = Manager.screenSize.x - width - right;
        float center_x = (Manager.screenSize.x - width) / 2;
        float left_x = left;
        float up_y = Manager.screenSize.y - height - top;
        float center_y = (Manager.screenSize.y - height) / 2;
        float down_y = down;

        float this_x, this_y;
        // Determine X
        switch(anchor){
            case CENTER:
            case DOWN:
            case UP:
                this_x = center_x;
                break;
            case LEFT:
            case UP_LEFT:
            case DOWN_LEFT:
                this_x = left_x;
                break;
            case RIGHT:
            case UP_RIGHT:
            case DOWN_RIGHT:
                this_x = right_x;
                break;
            default:
                this_x = 0;
                System.out.println("WARNING: UNHANDLED");

        }
        // Determine y:
        switch(anchor){
            case UP:
            case UP_RIGHT:
            case UP_LEFT:
                this_y = up_y;
                break;
            case DOWN_RIGHT:
            case DOWN_LEFT:
            case DOWN:
                this_y = down_y;
                break;
            case LEFT:
            case CENTER:
            case RIGHT:
                this_y = center_y;
                break;
            default:
                this_y = 0;
                System.out.println("WARNING: UNHANDLED");
        }
        rect = new Rectangle(this_x, this_y, width, height);
    }

}
