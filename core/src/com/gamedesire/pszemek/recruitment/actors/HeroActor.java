package com.gamedesire.pszemek.recruitment.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.utilities.AssetCache;

import net.dermetfan.gdx.assets.AnnotationAssetManager;

/**
 * Created by Ciemek on 30/04/16.
 */
public class HeroActor extends SpaceInvadersActor {


    public HeroActor(Vector2 location, Vector2 direction) {
        super(AssetCache.getHeroSprite(), location, direction);
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {
//        location.add(direction);
    }

    @Override
    public void dispose() {

    }

}
