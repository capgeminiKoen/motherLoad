package com.mygdx.game.Utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GUI.Anchor;

public final class Utility {
    private Utility(){

    }

    // Index to return when something is not found.
    public static final int NOT_FOUND = -1;

    public static int clamp(int number, int min, int max){
        return (number < min) ? min : ((number > max) ? max : number);
    }
    public static float clamp(float number, float min, float max){
        return (number < min) ? min : ((number > max) ? max : number);
    }

    public static void drawText(SpriteBatch batch, Anchor anchor, int left, int right, int top, int down, int width, int height, String text){
        // Set projection matrix for this thingy
        batch.setProjectionMatrix(Manager.camera.projection);
        // get the rectangle for drawing
        Rectangle rect = getRectangle(anchor, left, right, top, down, width, height);
        // Draw the text
        Manager.font.draw(batch, text, rect.x - Manager.screenSize.x / 2, rect.y - Manager.screenSize.y /2);
        // Set it back
        batch.setProjectionMatrix(Manager.camera.combined);
    }
    // Initialize rectangle for a menu.
    public static Rectangle getRectangle(Anchor anchor, int left, int right, int top, int down, int width, int height){
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
        return new Rectangle(this_x, this_y, width, height);
    }

}
