package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Ciemek on 29/04/16.
 */
public class TouchProcessorMobile extends TouchProcessor{


    //todo: ship should not TELEPORT to finger's position, but rather MOVE in this direction.

    public TouchProcessorMobile(Sprite sprite) {
        super(sprite);
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
//        sprite.setPosition(Gdx.input.getX() - sprite.getWidth()/2, Gdx.graphics.getHeight() - Gdx.input.getY() - sprite.getHeight()/2);
        sprite.setCenter(screenX, Gdx.graphics.getHeight() - screenY);
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