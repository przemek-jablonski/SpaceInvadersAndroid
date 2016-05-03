package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.HeroActor;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 30/04/16.
 */
public abstract class SpaceInvadersActor {

    protected Sprite    actorSprite;
    protected float     speedValue;
    protected float     accelerationValue;
    protected Vector2   directionVector;
    private   Vector2   temporaryMovementVector;
    protected long      rateOfFireIntervalMillis;
    protected long      lastFiredMillis;




    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, float directionX, float directionY) {
        this(actorSprite, new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        this(actorSprite, new Vector2(locationX, locationY), directionVector);
    }

    public SpaceInvadersActor(Sprite actorSprite, Vector2 location, Vector2 directionVector) {

        this.actorSprite = actorSprite;
        this.directionVector = directionVector;
        speedValue = 1;
        rateOfFireIntervalMillis = 500;
        lastFiredMillis = System.currentTimeMillis();
        actorSprite.setCenter(location.x, location.y);
        System.err.println("new actor created => pos: " + getActorPosition() + ", dir:" + getActorDirection());
        create();
    }


    //functional methods:
    public abstract void create();

    public abstract void update();

    public abstract void dispose();

    public void render(SpriteBatch batch) {
        batch.draw(actorSprite.getTexture(), getActorPosition().x, getActorPosition().y);
    }


    //accessors: setters:
    public void setDirection(Vector2 vector) {
        setDirection(vector.x, vector.y);
    }

    public void setDirection(float x, float y) {
        //v1:
//        directionVector.set(x * Gdx.graphics.getDeltaTime(), y * Gdx.graphics.getDeltaTime());

        //v2:
        directionVector.set(x, y);
    }

    public void setPosition(Vector2 vector) {
        setPosition(vector.x, vector.y);
    }

    public void setPosition(float x, float y) {
        actorSprite.setCenter(x, y);
    }

    public void updatePosition() {
//        temporaryMovementVector = new Vector2(
//                (directionVector.x * speedValue) * Gdx.graphics.getDeltaTime(),
//                (directionVector.y * speedValue) * Gdx.graphics.getDeltaTime());
        if (this instanceof HeroActor && !Gdx.input.isTouched())
                return;
            setPosition(
                    getActorPosition().x + (directionVector.x * speedValue) * Gdx.graphics.getDeltaTime(),
                    getActorPosition().y + (directionVector.y * speedValue) * Gdx.graphics.getDeltaTime());
    }

    public void setLastFiredMillis(long lastFiredMillis) {
        this.lastFiredMillis = lastFiredMillis;
    }


    //accessors: getters:

    public Vector2 getActorPosition() {
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
