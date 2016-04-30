package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Ciemek on 29/04/16.
 */
public abstract class TouchProcessor implements InputProcessor {

    protected Sprite    sprite;

    public TouchProcessor(Sprite sprite) {
        this.sprite = sprite;
    }

}
