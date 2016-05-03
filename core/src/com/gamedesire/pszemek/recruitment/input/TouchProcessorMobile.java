package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.Gdx;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorMobile extends TouchProcessor{

    //todo: ship should not TELEPORT to finger's position, but rather MOVE in this directionVector.

    @Override
    public void attachActorSpawner(ActorHolder actorHolder) {
        this.actorHolder = actorHolder;
        controlledActor = this.actorHolder.getHero();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        actorHolder.playerSpawnProjectile();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        sprite.setPosition(Gdx.input.getX() - sprite.getWidth()/2, Gdx.graphics.getHeight() - Gdx.input.getY() - sprite.getHeight()/2);
//        sprite.setCenter(screenX, Gdx.graphics.getHeight() - screenY);

        if (controlledActor == null) return false;

        controlledActor.setLocation(screenX, Gdx.graphics.getHeight() - screenY);
//        System.err.println("TouchMobile: location changed: " + controlledActor.getActorCenterPosition());
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
