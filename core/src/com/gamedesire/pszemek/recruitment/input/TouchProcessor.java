package com.gamedesire.pszemek.recruitment.input;

import com.badlogic.gdx.InputProcessor;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;

/**
 * Created by Ciemek on 29/04/16.
 */
public abstract class TouchProcessor implements InputProcessor {


    protected ActorHolder        actorHolder;
    protected SpaceInvadersActor controlledActor;
    protected boolean touchPressedDown = false;


    public abstract void attachActorSpawner(ActorHolder actorHolder);

    public boolean isTouchPressedDown() { return touchPressedDown; }


}
