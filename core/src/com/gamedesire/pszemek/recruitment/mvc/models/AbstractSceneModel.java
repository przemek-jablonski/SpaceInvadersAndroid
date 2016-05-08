package com.gamedesire.pszemek.recruitment.mvc.models;

import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;

/**
 * Created by Ciemek on 08/05/16.
 */
public abstract class AbstractSceneModel implements Disposable {


    protected ActorHolder actorHolder;
    protected boolean     touchRequest;


    public AbstractSceneModel() {
        actorHolder = new ActorHolder();
        touchRequest = false;
    }


    public abstract void create();

    public abstract void update(float deltaTime);

    public final ActorHolder getActorHolder() { return actorHolder; }

    public final boolean getTouchRequest() { return touchRequest; }

    public final void switchTouchRequest() {touchRequest = !touchRequest;}

    @Override
    public void dispose() {
        actorHolder.dispose();
    }

}
