package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 30/04/16.
 */
public abstract class SpaceInvadersActor {

    protected Sprite    actorSprite;
    protected float     speedValue;
    protected float     accelerationValue;
    protected Vector2   directionVector;
    protected long      rateOfFireIntervalMillis;
    protected long      lastFiredMillis;




    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, float directionX, float directionY) {
        this(actorSprite, new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        this(actorSprite, new Vector2(locationX, locationY), directionVector);
    }

    public SpaceInvadersActor(Sprite actorSprite, Vector2 location, Vector2 directionVector) {
        create();
        this.actorSprite = actorSprite;
        this.directionVector = directionVector;
        speedValue = 1;
        rateOfFireIntervalMillis = 500;
        lastFiredMillis = System.currentTimeMillis();
        actorSprite.setCenter(location.x, location.y);
        System.err.println("new actor created => pos: " + getActorCenterPosition() + ", dir:" + getActorDirection());
    }


    //functional methods:
    public abstract void create();

    public abstract void update();

    public abstract void dispose();

    public void render(SpriteBatch batch) {
        batch.draw(actorSprite.getTexture(), getActorCenterPosition().x, getActorCenterPosition().y);
    }


    //accessprs: setters:
    public void setDirection(float x, float y) {
        directionVector.set(x * Gdx.graphics.getDeltaTime(), y * Gdx.graphics.getDeltaTime());
    }

    public void setPosition(float x, float y) {
        actorSprite.setCenter(x, y);
    }

    public void updatePosition() {
        actorSprite.setPosition(actorSprite.getX() + (directionVector.x * speedValue),
               actorSprite.getY() + (directionVector.y * speedValue));

//        actorSprite.setPosition(
//                getActorCenterPosition().x + (directionVector.x * speedValue),
//                getActorCenterPosition().y + (directionVector.y * speedValue));
    }

    public void setLastFiredMillis(long lastFiredMillis) {
        this.lastFiredMillis = lastFiredMillis;
    }


    //accessors: getters:

    public Sprite getActorSprite() {
        return actorSprite;
    }

    public Vector2 getActorPosition() {
        return new Vector2(actorSprite.getX(), actorSprite.getY());
    }

    public Vector2 getActorCenterPosition() {
        return Utils.getCenterPosition(actorSprite);
    }

    public Vector2 getActorDirection() {
        return directionVector;
    }


    public long getRateOfFireIntervalMillis() {
        return rateOfFireIntervalMillis;
    }

    public long getLastFiredMillis() {
        return lastFiredMillis;
    }


}
