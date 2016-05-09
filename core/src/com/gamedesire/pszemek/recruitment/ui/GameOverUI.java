package com.gamedesire.pszemek.recruitment.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 09/05/16.
 */
public class GameOverUI extends AbstractBaseUI {

    private SpriteBatch spriteBatch;
    private Table       gameOverTable;
    private TextButton  restartButton;
    private Sprite      gameOverBackground;
    private boolean     restartRequested;

    public GameOverUI(SpriteBatch spriteBatch) {
        super(spriteBatch);
        this.spriteBatch = spriteBatch;
        restartRequested = false;
        create();
    }

    @Override
    public void create() {
        gameOverTable = new Table();
        gameOverTable.setFillParent(true);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        gameOverBackground = new Sprite(new Texture(Gdx.files.internal("gameover.png")));

        restartButton = new TextButton("restart?", skin);
        restartButton.bottom().pad(50f);
        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                restartRequested = true;
                return true;
            }
        });

        gameOverTable.row();
        gameOverTable.add(restartButton).height(75).width(250);

        stage.addActor(gameOverTable);
        Gdx.input.setInputProcessor(stage);
        update();
    }

    @Override
    public void update() {
    }

    @Override
    public void render(float deltaTime) {
        spriteBatch.begin();
        gameOverBackground.draw(spriteBatch);
        spriteBatch.end();
        stage.act(deltaTime);
        stage.draw();

    }

    public boolean isRestartRequested() {
        return restartRequested;
    }
}
