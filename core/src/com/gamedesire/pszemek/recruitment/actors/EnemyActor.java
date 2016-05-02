package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

/**
 * Created by Ciemek on 30/04/16.
 */
public class EnemyActor extends SpaceInvadersActor {





    public EnemyActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getEnemy001Sprite(), location, direction);
        //todo: rotate only enemy001 (i guess)
        actorSprite.setRotation(180);
        System.out.println("ENEMY CREATED");
    }


    public EnemyActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }


    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
