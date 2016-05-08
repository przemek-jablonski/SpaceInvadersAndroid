package com.gamedesire.pszemek.recruitment.mvc.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.screens.SpaceInvadersScreen;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorMobile extends AbstractTouchProcessor {

    //todo: ship should not TELEPORT to finger's position, but rather MOVE in this directionVector.

    Vector2             differenceVector;
    Vector3             screenVectorInput;


    @Override
    public void registerControlledActor(SpaceInvadersActor actor) {
        controlledActor = actor;
    }

    @Override
    public void registerCamera(Camera camera) {
        this.camera = camera;
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
        if(!registeredModel.getTouchRequest())
            registeredModel.switchTouchRequest();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(registeredModel.getTouchRequest())
            registeredModel.switchTouchRequest();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (controlledActor == null)
            return false;

        differenceVector = controlledActor.getActorPosition();

        screenVectorInput = camera.unproject(new Vector3(screenX, screenY, 0f));
        differenceVector.sub(screenVectorInput.x, screenVectorInput.y);
        differenceVector = Utils.transformVectorToDirection(differenceVector, 10);

        controlledActor.setDirection(differenceVector);
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
