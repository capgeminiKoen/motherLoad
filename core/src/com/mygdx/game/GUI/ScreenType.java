package com.mygdx.game.GUI;

public enum ScreenType {
    Inventory(new InventoryScreen());

    public Screen getScreen() {
        return screen;
    }

    private Screen screen;


    ScreenType(Screen screen){
        this.screen = screen;
    }
}
