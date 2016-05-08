package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;

/**
 * Created by Ciemek on 01/05/16.
 */
public class BonusItemActor extends SpaceInvadersActor implements IDamageable{

    protected boolean   dead;
    protected boolean   shoot;

    public BonusItemActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        super(actorSprite, locationX, locationY, directionVector);
    }

    @Override
    public void create() {
        System.err.println("BONUSITEM: create");
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
        System.err.println("BONUSITEM: onspawn");
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = maxShieldPoints;
        dead = false;
        shoot = false;
    }

    @Override
    public float onHit(float damageDealt) {
        System.err.println("BONUSITEM: ONHIT");
        actualHealthPoints = 0;
        if(!dead)
            onDeath();
        return 0;
    }

    @Override
    public void onDeath() {
        System.err.println("BONUSITEM: ondeath");
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        dead = true;
    }
}
