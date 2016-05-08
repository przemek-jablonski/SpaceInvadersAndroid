package com.gamedesire.pszemek.recruitment.mvc.controllers;

import com.badlogic.gdx.graphics.Camera;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorDesktop extends AbstractTouchProcessor {

    //todo: basically everything here, except for constructor ofc.

    @Override
    public void registerControlledActor(SpaceInvadersActor actor) {

    }

    @Override
    public void registerCamera(Camera camera) {

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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
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
