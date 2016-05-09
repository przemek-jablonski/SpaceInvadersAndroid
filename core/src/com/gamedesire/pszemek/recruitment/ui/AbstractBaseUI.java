package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 06/05/16.
 *
 * Base class for User Interface classes in the game.
 */
public abstract class AbstractBaseUI implements Disposable{


    protected Viewport viewport;
    protected Stage stage;


    public AbstractBaseUI(SpriteBatch spriteBatch) {
        viewport = new FitViewport(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        stage = new Stage(viewport, spriteBatch);
    }


    public abstract void create();

    public abstract void update();

    public abstract void render(float deltaTime);

    public final Stage getStage() { return stage; }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
