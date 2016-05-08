package com.gamedesire.pszemek.recruitment.mvc.models;

import com.badlogic.gdx.utils.Disposable;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;

/**
 * Created by Ciemek on 08/05/16.
 */
public abstract class AbstractSceneModel implements Disposable {

    protected ActorHolder actorHolder;

    public abstract void create();

    public abstract void update(float deltaTime);

    @Override
    public void dispose() {
        actorHolder.dispose();
    }
}
