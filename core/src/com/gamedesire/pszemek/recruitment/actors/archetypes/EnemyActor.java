package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;

/**
 * Created by Ciemek on 07/05/16.
 *
 * Base class for Enemies in the game
 */
public abstract class EnemyActor extends SpaceInvadersActor implements IDamageable {

    protected boolean spawned;
    protected boolean dead;
    protected boolean shoot;
    protected boolean visible;


    public EnemyActor(Sprite actorSprite, Vector2 location, Vector2 directionVector) {
        super(actorSprite, location, directionVector);
    }

    public EnemyActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        this(actorSprite, new Vector2(locationX, locationY), directionVector);
    }

    public EnemyActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector, int velocity) {
        this(actorSprite, new Vector2(locationX, locationY), directionVector);
        setVelocity(velocity);
    }

    @Override
    public void create() {
        spawned = false;
        dead = false;
        shoot = false;
        visible = true;
        onSpawn();
    }

    @Override
    public void update() {
        if (actualHealthPoints <= 0)
            if (!dead)
                onDeath();

        updatePosition();

        if (!shoot)
            shoot = true;

    }

    @Override
    public void dispose() {

    }


    @Override
    public void onSpawn() {
        dead = false;
        shoot = false;
    }

    @Override
    public float onHit(float damageDealt) {
        actualShieldPoints -= damageDealt;

        if (actualShieldPoints <= 0) {
            actualHealthPoints += actualShieldPoints;
            actualShieldPoints = 0;
            if(actualHealthPoints <= 0)
                actualHealthPoints = 0;
        }

        return actualHealthPoints;
    }

    @Override
    public void onDeath() {
//        System.err.println("DEATH, hp: " + actualHealthPoints + ", sp: " + actualShieldPoints);
        dead = true;
    }

    public void setVelocity(int velocityValue) {
        this.velocityValue = velocityValue;
    }


    public final boolean isDead() {
        return dead;
    }

    public final boolean isShoot() {
        return shoot;
    }

    public final void setShot() {
        shoot = false;
    }
}
