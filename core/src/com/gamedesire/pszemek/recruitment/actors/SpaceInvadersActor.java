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
        batch.draw(actorSprite.getTexture(), Utils.getCenterPosition(actorSprite).x, Utils.getCenterPosition(actorSprite).y);
    }

    public void setDirection(float x, float y) {
        direction.set(x * Gdx.graphics.getDeltaTime(), y * Gdx.graphics.getDeltaTime());
    }

    public void setLocation(float x, float y) {
        actorSprite.setCenter(x,y);
    }




//    public void setLocation(Vector2 location) {
//        this.location = location;
//        actorSprite.setCenter(location.x, location.y);
//    }

    //    todo: should be part of IDestroyable interface i guess?
//    public abstract void takeDamage();
//    public abstract void die();


//    public Sprite setSpriteCenterPos(Vector2 position) {
//        return setSpriteCenterPos(position.x, position.y);
//    }
//
//    public Sprite setSpriteCenterPos(float positionX, float positionY) {
//        actorSprite.setPosition(positionX, positionY);
//        return actorSprite;
//    }

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
