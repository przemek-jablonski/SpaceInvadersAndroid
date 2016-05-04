package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;

/**
 * Created by Ciemek on 30/04/16.
 */
public class EnemyActor extends SpaceInvadersActor implements IDamageable{



    public EnemyActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getEnemy001Sprite(), location, direction);
        //todo: rotate only enemy001 (i guess)
//        actorSprite.setRotation(180);
        System.err.println("ENEMY CREATED, pos: " + getActorPosition());
    }


    public EnemyActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    public EnemyActor(float locationX, float locationY, Vector2 direction) {
        this(new Vector2(locationX, locationY), direction);
    }


    @Override
    public void create() {
        speedValue = 100;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onSpawn() {
        actualHealthPoints = maxHealthPoints;
    }

    @Override
    public float onHit(float damageDealt) {

        actualShieldPoints -= damageDealt;

        if (actualShieldPoints <= 0) {


            actualHealthPoints += actualShieldPoints;
            actualShieldPoints = 0;
            if(actualHealthPoints <= 0) {
                actualHealthPoints = 0;
                onDeath();
            }

        }
        
        System.err.println("DAMAGE=> dmg: " + damageDealt + ", remaining: " + actualHealthPoints);
        return actualHealthPoints;
    }

    @Override
    public void onDeath() {

    }
}
