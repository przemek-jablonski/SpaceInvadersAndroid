package com.gamedesire.pszemek.recruitment.actors.primary;


import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 30/04/16.
 */
public class HeroActor extends SpaceInvadersActor implements IDamageable{


    public HeroActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getHeroSprite(), location, direction);

    }

    public HeroActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    @Override
    public void create() {
        rateOfFireIntervalMillis = Constants.RATEOFFIRE_INTERVAL_PLAYER_BASE;
        velocityValue = Constants.VELOCITY_VALUE_PLAYER;
        onSpawn();
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
        System.err.println("HERO SPAWNED");
        maxHealthPoints = 45;
        maxShieldPoints = 10;
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = maxShieldPoints;
    }

    @Override
    public float onHit(float damageDealt) {
//        System.err.println("HERO HIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return 0;
    }

    @Override
    public void onDeath() {
        System.err.println("HERO DIED ?!?!?!?!?!?!?!?!?!?!?!!?!?!??!");
    }
}
