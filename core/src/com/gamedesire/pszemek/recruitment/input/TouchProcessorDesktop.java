package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gamedesire.pszemek.recruitment.actors.SpaceInvadersActor;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorDesktop extends TouchProcessor {

    //todo: basically everything here, except for constructor ofc.


    @Override
    public void attachActor(SpaceInvadersActor actor) {
        this.actor = actor;
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
