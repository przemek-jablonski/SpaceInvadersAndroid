package com.gamedesire.pszemek.recruitment.mvc.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.mvc.models.AbstractSceneModel;
import com.gamedesire.pszemek.recruitment.mvc.models.SpaceInvadersSceneModel;

/**
 * Created by Ciemek on 29/04/16.
 */
public abstract class AbstractTouchProcessor implements InputProcessor {


    protected AbstractSceneModel    registeredModel;
    protected SpaceInvadersActor    controlledActor;
    protected Camera                camera;
    protected boolean               touchPressedDown = false;

    public void registerModel(AbstractSceneModel model) {
        registeredModel = model;
    }

    public abstract void registerControlledActor(SpaceInvadersActor actor);

    public abstract void registerCamera(Camera camera);

    public boolean isTouchPressedDown() { return touchPressedDown; }


}
