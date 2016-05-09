package com.gamedesire.pszemek.recruitment.actors.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ActorType;
import com.gamedesire.pszemek.recruitment.actors.archetypes.ProjectileActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 03/05/16.
 */
public class BoltProjectileActor extends ProjectileActor {


    public BoltProjectileActor(Vector2 location, ActorType actorType) {
        this(location, Constants.VECTOR_DIRECTION_UP, actorType);
    }

    public BoltProjectileActor(Vector2 location, Vector2 direction, ActorType actorType) {
        super(AssetRouting.getProjectileBoltSprite(), location, direction, actorType);
    }

    @Override
    public void create() {
        if (getActorType() == ActorType.HERO)
            velocityValue = Constants.VELOCITY_VALUE_PROJECTILE_BOLT_PLAYER;
        else
            velocityValue = Constants.VELOCITY_VALUE_PROJECTILE_BOLT;
        damageValue = 15;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void dispose() {

    }

}
