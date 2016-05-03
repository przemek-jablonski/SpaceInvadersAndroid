package com.gamedesire.pszemek.recruitment.actors;


import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

/**
 * Created by Ciemek on 30/04/16.
 */
public class HeroActor extends com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor {


    public HeroActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getHeroSprite(), location, direction);
        rateOfFireIntervalMillis = 200;
    }

    public HeroActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {
//        location.add(directionVector);
    }

    @Override
    public void dispose() {

    }

}
