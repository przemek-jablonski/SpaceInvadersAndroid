package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gamedesire.pszemek.recruitment.actors.SpaceInvadersActor;

/**
 * Created by Ciemek on 29/04/16.
 */
public abstract class TouchProcessor implements InputProcessor {

    protected SpaceInvadersActor actor;

    public abstract void attachActor(SpaceInvadersActor actor);

}
