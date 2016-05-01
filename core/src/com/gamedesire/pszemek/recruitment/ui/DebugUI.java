package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.Preferences;

/**
 * Created by Ciemek on 30/04/16.
 */
public class DebugUI {

    private Viewport viewport;
    private Stage   stage;

    private float   frames;
    private float   delta;
    private long    actualTime;
    private long    startTime;

    private Label   labelFrames;
    private Label   labelDelta;
    private Label   labelTimer;


    public DebugUI(SpriteBatch batch) {

        startTime = System.currentTimeMillis();
        frames = 0;
        delta = 0;

        viewport = new FitViewport(Preferences.PREF_WIDTH, Preferences.PREF_HEIGHT);
        stage = new Stage(viewport, batch);

        Table uiTable = new Table();
        uiTable.top();
        uiTable.setFillParent(true);

        labelFrames = new Label(Float.toString(frames), new LabelStyle(new BitmapFont(), Color.RED));
        labelDelta = new Label(Float.toString(delta), new LabelStyle(new BitmapFont(), Color.RED));
        labelTimer = new Label(Long.toString(actualTime), new LabelStyle(new BitmapFont(), Color.RED));

        uiTable.add(labelFrames).align(Align.right).right().padTop(10);
        uiTable.row();
        uiTable.add(labelDelta).align(Align.right).padTop(10);
        uiTable.row();
        uiTable.add(labelTimer).align(Align.right).padTop(10);
        uiTable.row();

        stage.addActor(uiTable);

        render();
    }

    //todo: make this abstract class in superUI class or something
    public void render() {
        actualTime = System.currentTimeMillis() - startTime;
        actualTime /= 1000;
        frames = Gdx.graphics.getFramesPerSecond();
        delta = Gdx.graphics.getDeltaTime();

    }


    public Stage getStage() {
        return stage;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
