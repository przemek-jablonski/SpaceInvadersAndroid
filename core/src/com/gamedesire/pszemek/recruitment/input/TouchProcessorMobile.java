package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorMobile extends TouchProcessor {

    //todo: ship should not TELEPORT to finger's position, but rather MOVE in this directionVector.

    Vector2 differenceVector;


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
        if (!touchPressedDown)
            touchPressedDown = true;
//        actorHolder.playerSpawnProjectile();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (touchPressedDown) touchPressedDown = false;
        return true;
    }

    //origin of screenX and screenY is in TOP LEFT CORNER (...goddamnit)
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        //v1:
        if (controlledActor == null) return false;
//        controlledActor.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
//        return true;

        //v2:
//        if (controlledActor == null) return false;

//        System.err.print("TOUCHDIFF: ");
        differenceVector = controlledActor.getActorPosition();
//        System.err.print("pos: " + differenceVector + ", ");
        Vector2 screenVector = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
//        System.err.print("screen: " + "(" + screenVector + ", ");
        differenceVector.sub(screenVector);
//        System.err.print("diff: " + differenceVector);
        differenceVector = Utils.transformVectorToDirection(differenceVector, 10);
//        System.err.print("dir: " + differenceVector + "\n");

        controlledActor.setDirection(differenceVector);
        System.err.println("touchpos: " + screenVector);

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
