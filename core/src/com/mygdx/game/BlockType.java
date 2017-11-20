package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;

public enum BlockType {

    Normal("block.png"),
    Cracked("block_cracked.png"),
    CrackedSevere("block_cracked_severe.png"),
    Unbreakable("stones.png", false, false),
    Stone("stones.png", false, true),
    Empty();

    public Texture blockTexture;
    private boolean drillable, canBlowUp;

    BlockType(String texturePath){
        System.out.println("Called constructor for texturepath " + texturePath);
        blockTexture = new Texture(texturePath);
        drillable = true;
        canBlowUp = true;
    }
    BlockType(String texturePath, boolean drillable, boolean canBlowUp){
        this(texturePath);
        this.drillable = drillable;
        this.canBlowUp = canBlowUp;
    }
    BlockType(){
        blockTexture = null;
    }

    public boolean isDrillable() {
        return drillable;
    }

    public void setDrillable(boolean drillable) {
        this.drillable = drillable;
    }

    public boolean isCanBlowUp() {
        return canBlowUp;
    }

    public void setCanBlowUp(boolean canBlowUp) {
        this.canBlowUp = canBlowUp;
    }
}
