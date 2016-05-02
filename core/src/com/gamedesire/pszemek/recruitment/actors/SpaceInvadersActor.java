package com.gamedesire.pszemek.recruitment.actors;

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
    protected Vector2   direction;
    protected Vector2   accelerationVector;
    protected float     accelerationValue;


    public SpaceInvadersActor() {

    }

    public SpaceInvadersActor(Sprite actorSprite, float locationX, float locationY, float directionX, float directionY) {
        this(actorSprite, new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    public SpaceInvadersActor(Sprite actorSprite, Vector2 location, Vector2 direction) {
        this();
        this.actorSprite = actorSprite;
        this.direction = direction;
        actorSprite.setCenter(location.x, location.y);
    }

    public abstract void create();

    public abstract void update();

    public abstract void dispose();


    public void render(SpriteBatch batch) {
        batch.draw(actorSprite.getTexture(), getActorCenterPosition().x, getActorCenterPosition().y);
    }

    public void setDirection(float x, float y) {
        direction.set(x * Gdx.graphics.getDeltaTime(), y * Gdx.graphics.getDeltaTime());
    }

    public void setLocation(float x, float y) {
        actorSprite.setCenter(x,y);
    }




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
        return direction;
    }

    public Vector2 getAccelerationVector() {
        return accelerationVector;
    }

    public float getAccelerationValue() {
        return accelerationValue;
    }

}
