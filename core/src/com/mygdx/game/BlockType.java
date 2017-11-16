package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;

public enum BlockType {

    Normal("block.png"),
    Cracked("block_cracked.png"),
    CrackedSevere("block_cracked_severe.png"),
    Unbreakable("block_unbreakable.png"),
    Stone("block_unbreakable.png"),
    Empty();

    public Texture blockTexture;
    BlockType(String texturePath){
        System.out.println("Called constructor for texturepath " + texturePath);
        blockTexture = new Texture(texturePath);
    }
    BlockType(){
        blockTexture = null;
    }


}
