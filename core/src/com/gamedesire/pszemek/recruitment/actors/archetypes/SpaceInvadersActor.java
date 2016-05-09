package com.gamedesire.pszemek.recruitment.actors.archetypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.primary.HeroActor;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 30/04/16.
 *
 * Superclass for every living entity (Actor) in the game.
 * Provides guidelines of work for extending Actors.
 * Holds and encapsulates uniform actor stats.
 */
public abstract class SpaceInvadersActor {

    protected Sprite    actorSprite;
    protected float     velocityValue;
    protected float     directionX;
    protected float     directionY;
    protected long      rateOfFireIntervalMillis;
    protected long      lastFiredMillis;

    protected int       actualHealthPoints;
    protected int       actualShieldPoints;
    protected int       maxHealthPoints;
    protected int       maxShieldPoints;



    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, float directionX, float directionY) {
        this(actorSprite, new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, Vector2 directionVector) {
        this(actorSprite, new Vector2(locationX, locationY), directionVector);
    }

    public SpaceInvadersActor(Sprite actorSprite, Vector2 location, Vector2 directionVector) {

        this.actorSprite = actorSprite;
        directionX = directionVector.x;
        directionY = directionVector.y;
        velocityValue = 1;
        rateOfFireIntervalMillis = 500;
        lastFiredMillis = System.currentTimeMillis();
        actorSprite.setCenter(location.x, location.y);
        create();
    }


    //functional methods:
    public abstract void create();

    public abstract void update();

    public abstract void dispose();


    //accessors: setters:
    public void setDirection(Vector2 vector) {
        setDirection(vector.x, vector.y);
    }

    public void setDirection(float x, float y) {
        directionX = x;
        directionY = y;
    }

    public void setPosition(float x, float y) {
        actorSprite.setCenter(x, y);
    }

    public void updatePosition() {
        if (this instanceof HeroActor && !Gdx.input.isTouched()) return;
            setPosition(
                    getActorPosition().x + (directionX * velocityValue) * Gdx.graphics.getDeltaTime(),
                    getActorPosition().y + (directionY * velocityValue) * Gdx.graphics.getDeltaTime());
    }

    public void setLastFiredMillis(long lastFiredMillis) {
        this.lastFiredMillis = lastFiredMillis;
    }

    public void updateVelocity(int percentage) {
        velocityValue *= 1f + (percentage / 100f);
    }

    public void updateRateOfFireInterval(int percentage) {
        rateOfFireIntervalMillis /= 1f + (percentage / 100f);
    }

    //accessors: getters:
    public Vector2 getActorPosition() {
        return Utils.getCenterPosition(actorSprite);
    }

    public Vector2 getActorDirection() {
        return new Vector2(directionX, directionY);
    }

    public long getRateOfFireIntervalMillis() {
        return rateOfFireIntervalMillis;
    }

    public long getLastFiredMillis() {
        return lastFiredMillis;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(actorSprite.getX(), actorSprite.getY(), actorSprite.getTexture().getWidth(), actorSprite.getTexture().getHeight());
    }

    public int getActualHealthPoints() {
        return actualHealthPoints;
    }

    public int getActualShieldPoints() {
        return actualShieldPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getMaxShieldPoints() {
        return maxShieldPoints;
    }

    public Sprite getActorSprite() {
        return actorSprite;
    }
}
