package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.utilities.Const;

/**
 * Created by Ciemek on 06/05/16.
 */
public abstract class AbstractBaseUI {

    protected Viewport viewport;
    protected Stage stage;



    public AbstractBaseUI(SpriteBatch spriteBatch) {
        viewport = new FitViewport(Const.PREF_WIDTH, Const.PREF_HEIGHT);
        stage = new Stage(viewport, spriteBatch);
    }


    public abstract void create();


    public abstract void update();


    public abstract void render();


    public void dispose() {
        stage.dispose();
    }
}
