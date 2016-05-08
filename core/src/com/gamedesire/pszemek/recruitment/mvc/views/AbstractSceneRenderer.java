package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ciemek on 08/05/16.
 */
public abstract class AbstractSceneRenderer implements Disposable {

    protected SpriteBatch   spriteBatch;
    protected Camera        camera;
    protected Viewport      viewport;


    protected abstract void create();

    public abstract void render(float deltaTime);

    public abstract void resize(int width, int height);

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
