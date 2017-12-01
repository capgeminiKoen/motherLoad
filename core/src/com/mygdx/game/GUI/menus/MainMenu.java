package com.mygdx.game.GUI.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GUI.Screen;

public class MainMenu extends Screen {

    public MainMenu(){
        super();
        title = "Main Menu";
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        drawTitle(batch);
    }
}
