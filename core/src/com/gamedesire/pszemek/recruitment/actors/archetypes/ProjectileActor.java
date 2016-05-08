package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ciemek on 03/05/16.
 */
public abstract class ProjectileActor extends SpaceInvadersActor {

    private ActorType   actorType;
    protected float     damageValue;


    public ProjectileActor(Sprite actorSprite, Vector2 location, Vector2 directionVector, ActorType actorType) {
        super(actorSprite, location, directionVector);
        this.actorType = actorType;
    }

    public float getDamageValue() {
        return damageValue;
    }

    public ActorType getActorType() {
        return actorType;
    }
}
