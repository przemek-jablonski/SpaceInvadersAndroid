package com.gamedesire.pszemek.recruitment.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Ciemek on 30/04/16.
 */
public abstract class SpaceInvadersActor {

    protected Sprite    actorSprite;
//    protected Vector2   position;
    protected Vector2   direction;
    protected Vector2   accelerationVector;
    protected float     accelerationValue;


    public SpaceInvadersActor() {
    }

    public SpaceInvadersActor(Sprite actorSprite, Vector2 position, Vector2 direction) {
        this();
        this.actorSprite = actorSprite;
        this.direction = direction;
        actorSprite.setCenter(position.x, position.y);

    }


    public void render(SpriteBatch batch) {
        batch.draw(actorSprite.getTexture(), actorSprite.getX(), actorSprite.getY());
    }


    public abstract void update();

    public abstract void dispose();





    //todo: should be part of IDestroyable interface i guess?
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

    public Vector2 getDirection() {
        return direction;
    }

    public Vector2 getAccelerationVector() {
        return accelerationVector;
    }

    public float getAccelerationValue() {
        return accelerationValue;
    }
}
