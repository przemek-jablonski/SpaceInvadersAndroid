package com.gamedesire.pszemek.recruitment.actors.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;


/**
 * Created by Ciemek on 03/05/16.
 */
public class BulletProjectileActor extends SpaceInvadersActor {


    public BulletProjectileActor(float locationX, float locationY) {
        this(new Vector2(locationX, locationY));
    }

    public BulletProjectileActor(Vector2 location) {
        super(AssetRouting.getProjectileRocketSprite(), location, Constants.VECTOR_DIRECTION_UP);
        System.err.println("BULLET CREATED");
    }

    @Override
    public void create() {
        speedValue = 400;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void dispose() {

    }
}
