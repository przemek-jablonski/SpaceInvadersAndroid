package com.gamedesire.pszemek.recruitment.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.ui.AbstractBaseUI;
import com.gamedesire.pszemek.recruitment.ui.MainMenuUI;
import com.gamedesire.pszemek.recruitment.utilities.GameStateEnum;

/**
 * Created by Ciemek on 01/05/16.
 */
public class MainMenuScreen implements Screen {

    private MainMenuUI      sceneUI;
    private MainGameClass   game;

    public MainMenuScreen(MainGameClass game) {
        this.game = game;
        sceneUI = new MainMenuUI(game.getSpriteBatch());
    }

    @Override
    public void show() {
        sceneUI.create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(sceneUI.isTutorialCompleted()) {
            game.gameState = GameStateEnum.SPACE_INVADERS;
            game.stateChanged();
        }

        sceneUI.update();
        sceneUI.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
