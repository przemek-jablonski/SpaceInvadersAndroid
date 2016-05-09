package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;

/**
 * Created by Ciemek on 01/05/16.
 * Base class for collectible items (pickups, eg. Space Deer) in a game.
 */
public class BonusItemActor extends SpaceInvadersActor implements IDamageable{

    protected boolean   dead;
    protected boolean   shoot;

    public BonusItemActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        super(actorSprite, locationX, locationY, directionVector);
    }

    @Override
    public void create() {
        maxHealthPoints = 1;
        maxShieldPoints = 0;
        onSpawn();
    }

    @Override
    public void update() {
        updatePosition();

        if (actualHealthPoints <= 0)
            if (!dead)
                onDeath();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onSpawn() {
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = maxShieldPoints;
        dead = false;
        shoot = false;
    }

    @Override
    public float onHit(float damageDealt) {
        actualHealthPoints = 0;
        if(!dead)
            onDeath();
        return 0;
    }

    @Override
    public void onDeath() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        dead = true;
    }
}
