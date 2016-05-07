package com.gamedesire.pszemek.recruitment.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;

/**
 * Created by Ciemek on 03/05/16.
 */
public class BoltProjectileActor extends SpaceInvadersActor {

    public BoltProjectileActor(Sprite actorSprite, float locationX, float locationY, float directionX, float directionY) {
        super(actorSprite, locationX, locationY, directionX, directionY);
    }

    public BoltProjectileActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        super(actorSprite, locationX, locationY, directionVector);
    }

    public BoltProjectileActor(Sprite actorSprite, Vector2 location, Vector2 directionVector) {
        super(actorSprite, location, directionVector);
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
